import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

public class ArabicRomanConversion extends JFrame
{
    
    private MaskFormatter arabicFormat = new MaskFormatter();
    private JFormattedTextField arabicText = new JFormattedTextField(arabicFormat);
    
    private MaskFormatter romanFormat = new MaskFormatter();
    private JFormattedTextField romanText = new JFormattedTextField(romanFormat);
    
    public static void main(String args[])
    {
        new ArabicRomanConversion("Roman <--> Arabic");
    }

    /**
     * Constructs the main JFrame,
     *
     * @param title sets the title of the window.
     */

    private ArabicRomanConversion(String title)
    {
        //Sets initial values, position, and exit behavior
        super(title);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,1));

        //Creates a panel for the arabic text box and label
        JPanel arabicPanel = new JPanel();
        arabicPanel.add(new JLabel("Arabic numeral: "));
        arabicText.setActionCommand("A");
        arabicText.setColumns(12);
        try
        {
            arabicFormat.setMask("####");
        }
        catch(ParseException e)
        {
            System.err.print("Something broke... ");
        }
        arabicFormat.setValidCharacters("0123456789");
        //Adds key listener. I used keyReleased because keyTyped was just
        // consuming my input and I'm not sure why.
        arabicText.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
            
            }
    
            @Override
            public void keyPressed(KeyEvent e)
            {
            
            }
    
            @Override
            public void keyReleased(KeyEvent e)
            {
                romanText.setValue(arabicToRoman());
                romanText.revalidate();
                revalidate();
            }
        });
        arabicPanel.add(arabicText);
        add(arabicPanel);
        
        JPanel romanPanel = new JPanel();
        romanPanel.add(new JLabel("Roman numeral: "));
//        romanText.setActionCommand("R");
        romanText.setColumns(12);
        //Have to do a try-catch
        try
        {
            romanFormat.setMask("UUUUUUUUUUUUU");
        }catch(ParseException e) { System.err.print("Something broke... "); }
        romanFormat.setValidCharacters("IVXLCDM");
        //Adds key listener. Uses keyReleased because keyTyped was consuming
        // input.
        romanText.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
            
            }
    
            @Override
            public void keyPressed(KeyEvent e)
            {
            
            }
    
            @Override
            public void keyReleased(KeyEvent e)
            {
                arabicText.setValue("" + romanToArabic());
            }
        });
        romanPanel.add(romanText);
        add(romanPanel);
        
        pack();
        setVisible(true);
    }

    /**
     * Converts from arabic to roman.
     *
     * @return roman version of arabic numeral contained in arabic text field.
     */

    private String arabicToRoman()
    {
        if(arabicText.getText().trim().length() == 0)
        {
            return "";
        }
        int arabic = Integer.parseInt(arabicText.getText().trim());
        //Lowers the text field to the max if exceeded.
        if(arabic > 3999)
        {
            arabic = 3999;
            arabicText.setValue("" + arabic);
        }
        StringBuilder roman = new StringBuilder();
        while(arabic > 0)
        {
            if(arabic / 1000 >= 1)
            {
                roman.append("M");
                arabic -= 1000;
            }
            else if(arabic / 900 >= 1)
            {
                roman.append("CM");
                arabic -= 900;
            }
            else if(arabic / 500 >= 1)
            {
                roman.append("D");
                arabic -= 500;
            }
            else if(arabic / 400 >= 1)
            {
                roman.append("CD");
                arabic -= 400;
            }
            else if(arabic / 100 >= 1)
            {
                roman.append("C");
                arabic -= 100;
            }
            else if(arabic / 90 >= 1)
            {
                roman.append("XC");
                arabic -= 90;
            }
            else if(arabic / 50 >= 1)
            {
                roman.append("L");
                arabic -= 50;
            }
            else if(arabic / 40 >= 1)
            {
                roman.append("XL");
                arabic -= 40;
            }
            else if(arabic / 10 >= 1)
            {
                roman.append("X");
                arabic -= 10;
            }
            else if(arabic / 9 >= 1)
            {
                roman.append("IX");
                arabic -= 9;
            }
            else if(arabic / 5 >= 1)
            {
                roman.append("V");
                arabic -= 5;
            }
            else if(arabic / 4 >= 1)
            {
                roman.append("IV");
                arabic -= 4;
            }
            else
            {
                roman.append("I");
                arabic -= 1;
            }
        }
        return roman.toString();
    }

    /**
     * Converts from roman numeral to arabic numeral.
     *
     * @return Returns the conversion of the roman text field to arabic. Does
     * not account for characters out of order.
     */

    private String romanToArabic()
    {
        String roman = romanText.getText();
        if(roman.trim().length() == 0)
        {
            return "";
        }
        int total = 0;
        String sub;
        for(int i = 0; i < roman.length() - 1; i++)
        {
            sub = roman.substring(i, i+2);
            if(sub.equals("CM"))
            {
                total += 900;
                i++;
            }
            else if(sub.equals("CD"))
            {
                total += 400;
                i++;
            }
            else if(sub.equals("XC"))
            {
                total += 90;
                i++;
            }
            else if(sub.equals("XL"))
            {
                total += 40;
                i++;
            }
            else if(sub.equals("IX"))
            {
                total += 9;
                i++;
            }
            else if(sub.equals("IV"))
            {
                total += 4;
                i++;
            }
            else
            {
                sub = "" + roman.charAt(i);
                if(sub.equals("M"))
                {
                    total+= 1000;
                }
                else if(sub.equals("D"))
                {
                    total += 500;
                }
                else if(sub.equals("C"))
                {
                    total += 100;
                }
                else if(sub.equals("L"))
                {
                    total += 50;
                }
                else if(sub.equals("X"))
                {
                    total += 10;
                }
                else if(sub.equals("V"))
                {
                    total += 5;
                }
                else if(sub.equals("I"))
                {
                    total += 1;
                }
            }
        }
        return "" + total;
    }
}
