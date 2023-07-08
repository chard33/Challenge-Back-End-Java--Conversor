package com.Alura.conversor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class conversor extends JFrame{
   
   private JPanel panel;
   private JTabbedPane tabbedPane1;
   private JButton btnS;
   private JTextField cantidad;
   private JComboBox comboBox1;
   private JComboBox comboBox2;
   private JButton convertirButton;
   private JLabel resultado;
   private JLabel resultado2;
   private JTextField cantidadC;
   private JComboBox comboBox3;
   private JComboBox comboBox4;
   private JComboBox comboBox5;
   private JButton convertirButton1;
   private JLabel medida;
   private JLabel result;
   private JLabel moverP;
   private String apiD, apiM;
   private int posX, posY;
   private DecimalFormat df;

   public conversor(){
      
      setSize(250,
              333);
      setUndecorated(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      
      btnS.setBorder(null);
      
      comboBox1.setPrototypeDisplayValue("XXXXXXXXXX");
      comboBox2.setPrototypeDisplayValue("XXXXXXXXXX");
      
      resultado.setText("------");
      resultado2.setText("------");

      df = new DecimalFormat("0.000");
      
      setContentPane(panel);
      
      setVisible(true);
      
      try{
         
         btnS.setIcon(new ImageIcon(
                 ImageIO.
                         read(
                                 new File("./src/imagenes/btnSalir.png")
                         )
                         .getScaledInstance(
                                 btnS.getWidth(),
                                 btnS.getHeight(),
                                 1)));
      }catch(IOException e){
         throw new RuntimeException(e);
      }
      
      btnS.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e){
            System.exit(0);
         }
      });
      btnS.addMouseListener(new MouseAdapter(){
         @Override
         public void mouseEntered(MouseEvent e){
            btnS.setContentAreaFilled(true);
         }
         
         @Override
         public void mouseExited(MouseEvent e){
            btnS.setContentAreaFilled(false);
         }
      });
      
      cantidad.addKeyListener(new KeyAdapter(){
         @Override
         public void keyPressed(KeyEvent e){
            cantidad.setBorder(new JTextField().getBorder());
         }
      });
      
      cantidadC.addKeyListener(new KeyAdapter(){
         @Override
         public void keyPressed(KeyEvent e){
            cantidadC.setBorder(new JTextField().getBorder());
         }
      });
      
      moverP.addMouseListener(new MouseAdapter(){
         @Override
         public void mouseEntered(MouseEvent e){
            setCursor(13);
         }
         
         @Override
         public void mouseExited(MouseEvent e){
            setCursor(0);
         }
      });
      
      moverP.addMouseListener(new MouseAdapter(){
         @Override
         public void mousePressed(MouseEvent e){
            posX = e.getX();
            posY = e.getY();
         }
      });
      
      moverP.addMouseMotionListener(new MouseMotionAdapter(){
         @Override
         public void mouseDragged(MouseEvent e){
            setLocation(getLocation().x + e.getX() - posX,
                        getLocation().y + e.getY() - posY);
            
         }
      });
   }
   
   public static void main(String[] args){

      conversor conv;
      
      int opc =JOptionPane.showConfirmDialog(
              null,
              "Ingresar a programa\n(APIKEY = YES)\n(NORMAL = NO)",
              "SELECCION DE PROGRAMA",
              JOptionPane.YES_NO_OPTION,
              0);
      
      if(opc == 0){

         conv = new conversor();

         conv.convertidorApi();
         
      }else if(opc == 1){

         conv = new conversor();

         conv.convertidorNormal();
      }else{
         
         JOptionPane.showMessageDialog(null,
                                       "Terminando......",
                                       "Saliendo del programa",
                                       1);
         System.exit(0);
      }
   }
   
   public boolean soloNumeros(String numero){
      
      try{
         
         Double.parseDouble(numero);
         return true;
      }catch(Exception ex){
         return false;
      }
   }
   
   public void convertidorApi(){
      
      solicitarApis solicitarA = new solicitarApis();
      
      apiD = solicitarA.getApi1();
      apiM = solicitarA.getApi2();
      
      conexionConversionDivisas conn = new conexionConversionDivisas(
              "https://v6.exchangerate-api.com/v6/" +
                      apiD +
                      "/codes");
      
      conn.obtenerArrays();
      
      conexionConversionMedidas conn2 = new conexionConversionMedidas(
              "https://measurement-unit-converter.p.rapidapi.com/measurements/detailed",
              apiM);
      
      conn2.obtenerNombres();
      
      for(String moneda : conn.getValores()){
         
         comboBox1.addItem(moneda);
         comboBox2.addItem(moneda);
      }
      
      for(String nombre : conn2.getTipoMedida()){
         
         comboBox3.addItem(nombre);
      }
      
      for(Object obj : ((Object[]) conn2.getMedidas()
                                        .get(0))){
         
         comboBox4.addItem(((String[]) obj)[1]);
         comboBox5.addItem(((String[]) obj)[1]);
      }
      
      convertirButton.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e){
            
            if(! cantidad.getText()
                         .isEmpty() && soloNumeros(cantidad.getText())){
               
               conexionConversionDivisas conn2 = new conexionConversionDivisas(
                       "https://v6.exchangerate-api" +
                               ".com/v6/" +
                               apiD
                               + "/pair/" +
                               conn.getLlaves()[comboBox1.getSelectedIndex()] + "/" +
                               conn.getLlaves()[comboBox2.getSelectedIndex()] + "/" +
                               cantidad.getText());
               
               conn2.obtenerConversion();
               
               resultado.setText(cantidad.getText() +
                                         " " +
                                         conn.getLlaves()[comboBox1.getSelectedIndex()]);
               
               resultado2.setText(conn2.getResultado() +
                                          " " +
                                          conn.getLlaves()[comboBox2.getSelectedIndex()]);
               
            }else{
               
               cantidad.setBorder(BorderFactory.createMatteBorder(
                       1,
                       5,
                       1,
                       1,
                       Color.red));
            }
         }
      });
      
      comboBox3.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e){
            
            comboBox4.removeAllItems();
            comboBox5.removeAllItems();
            
            for(Object obj : ((Object[]) conn2.getMedidas()
                                              .get(comboBox3.getSelectedIndex()))){
               
               comboBox4.addItem(((String[]) obj)[1]);
               comboBox5.addItem(((String[]) obj)[1]);
            }
         }
      });
      
      convertirButton1.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e){
            
            if(! cantidadC.getText()
                          .isEmpty() && soloNumeros(cantidadC.getText())){
               
               String medida1 = (String) ((Object[]) ((Object[]) conn2.getMedidas()
                                                                      .get(comboBox3.getSelectedIndex()))[comboBox4.getSelectedIndex()])[0];
               String medida2 = (String) ((Object[]) ((Object[]) conn2.getMedidas()
                                                                      .get(comboBox3.getSelectedIndex()))[comboBox5.getSelectedIndex()])[0];
               
               conexionConversionMedidas conn3 = new conexionConversionMedidas(
                       "https://measurement-unit-converter.p.rapidapi.com/" +
                               comboBox3.getSelectedItem() +
                               "?value=" +
                               cantidadC.getText() +
                               "&from=" +
                               medida1 +
                               "&to=" +
                               medida2,
                       apiM
               );
               
               conn3.obtenerResultado();
               
               medida.setText(cantidadC.getText() + " " + medida1);
               
               result.setText(conn3.getResultado());
               
            }else{
               
               cantidadC.setBorder(BorderFactory.createMatteBorder(
                       1,
                       5,
                       1,
                       1,
                       Color.red));
            }
         }
      });
   }

   public void convertidorNormal(){

      ConversionNormal conversorN = new ConversionNormal();

      for (String[] conversiones: conversorN.getDivisas()) {

         comboBox1.addItem(conversiones[0] + " a " + conversiones[1]);
      }
      for (String[] conversiones: conversorN.getTemperaturas()) {

         comboBox4.addItem(conversiones[0] + " a " + conversiones[1]);
      }
      comboBox2.setEnabled(false);
      comboBox3.setEnabled(false);
      comboBox5.setEnabled(false);

      convertirButton.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e){

            if(!cantidad.getText()
                    .isEmpty() && soloNumeros(cantidad.getText())){

               resultado.setText(cantidad.getText()+
                       " "+
                       conversorN.getDivisas()[comboBox1.getSelectedIndex()][0]);
               
               resultado2.setText(df.format(conversorN.obteneResultadoD(
                       Double.parseDouble(cantidad.getText()),
                       comboBox1.getSelectedIndex())) + " "  +
                       conversorN.getDivisas()[comboBox1.getSelectedIndex()][1]);
            }else{

               cantidad.setBorder(BorderFactory.createMatteBorder(
                       1,
                       5,
                       1,
                       1,
                       Color.red));
            }
         }
      });

      convertirButton1.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e){

            if(!cantidadC.getText()
                    .isEmpty() && soloNumeros(cantidadC.getText())){

               medida.setText(cantidadC.getText()+
                       " "+
                       conversorN.getTemperaturas()[comboBox4.getSelectedIndex()][0]);

               result.setText(df.format(conversorN.obteneResultadoT(
                       Double.parseDouble(cantidadC.getText()),
                       comboBox4.getSelectedIndex())) + " "  +
                       conversorN.getTemperaturas()[comboBox4.getSelectedIndex()][1]);
            }else{

               cantidadC.setBorder(BorderFactory.createMatteBorder(
                       1,
                       5,
                       1,
                       1,
                       Color.red));
            }
         }
      });
   }
}
