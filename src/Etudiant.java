import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Etudiant {
    private JTextField txtnom;
    private JTextField txtprenom;
    private JTextField txtadresse;
    private JTextField txttelephone;
    private JButton ENREGISTRERButton;
    private JButton SUPPRIMERButton;
    private JButton MODIFIERButton1;
    private JButton RECHERCHERButton;
    private JTable table1;
    private JTextField RECHERCHEPARNOM;
    private JPanel Etudiant;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Etudiant");
        frame.setContentPane(new Etudiant().Etudiant);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    public  void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/etudiant","root","");
            System.out.println("Ok connexion établie");

        }
        catch (ClassNotFoundException ex)
        {

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Etudiant(){
        connect();
        table_etudiant();


        ENREGISTRERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom, prenom,adresse,telephone;

                nom = txtnom.getText();
                prenom = txtprenom.getText();
                adresse = txtadresse.getText();
                telephone =txttelephone.getText();


                try{
                    pst = con.prepareStatement("INSERT INTO etudiant (nom, prenom, adresse,telephone) VALUES(?,?,?,?)");
                    pst.setString(1,nom);
                    pst.setString(2,prenom);
                    pst.setString(3,adresse);
                    pst.setString(4,telephone);


                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Membre  Ajouter ! ! !");
                    table_etudiant();
                    txtnom.setText("");
                    txtprenom.setText("");
                    txtadresse.setText("");
                    txttelephone.setText("");
                    txtnom.requestFocus();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }





            }
        });

        RECHERCHERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String idEtudiant = RECHERCHEPARNOM.getText();

                    pst = con.prepareStatement("SELECT nom, prenom, adresse,telephone FROM etudiant WHERE nom = ?");
                    pst.setString(1,idEtudiant);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()==true)
                    {
                        String nom = rs.getString(1);
                        String prenom = rs.getString(2);
                        String adresse = rs.getString(3);
                        String telephone = rs.getString(4);



                        txtnom.setText(nom);
                        txtprenom.setText(prenom);
                        txtadresse.setText(adresse);
                        txttelephone.setText(adresse);
                    }
                    else {
                        txtnom.setText("");
                        txtprenom.setText("");
                        txtadresse.setText("");
                        txttelephone.setText("");
                        JOptionPane.showMessageDialog(null,"ce nom est incorrecte");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }



        });
        MODIFIERButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                String nom, prenom,adresse,telephone;

                nom = txtnom.getText();
                prenom = txtprenom.getText();
                adresse = txtadresse.getText();
                telephone = txttelephone.getText();

                String etudiantRechercher = RECHERCHEPARNOM.getText();






                try{
                    pst=con.prepareStatement("UPDATE etudiant SET nom = ?, prenom = ?, adresse = ? ,telephone = ? WHERE nom = ?");
                    pst.setString(1,nom);
                    pst.setString(2,prenom);
                    pst.setString(3,adresse);
                    pst.setString(4,telephone);
                    pst.setString(5,etudiantRechercher);



                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Mis a jour effectuer ! ! !");

                    table_etudiant();

                    txtnom.setText("");
                    txtprenom.setText("");
                    txtadresse.setText("");
                    txttelephone.setText("");
                    txtnom.requestFocus();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        SUPPRIMERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String etudiantRechercher = RECHERCHEPARNOM.getText();;

                try{

                    pst = con.prepareStatement("DELETE FROM etudiant WHERE id= ?");

                    pst.setString(1,etudiantRechercher);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"etudiant supprimer avec success");

                    table_etudiant();

                    txtnom.setText("");
                    txtprenom.setText("");
                    txtadresse.setText("");
                    txttelephone.setText("");
                    txtnom.requestFocus();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    void table_etudiant(){

        try{
            pst = con.prepareStatement("SELECT * FROM etudiant");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

