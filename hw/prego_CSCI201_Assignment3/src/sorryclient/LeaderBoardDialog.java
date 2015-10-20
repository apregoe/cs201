package sorryclient;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;

/**
 * Created by albertoprego on 10/13/15.
 */
class LeaderBoardDialog extends JDialog {
    LeaderBoardDialog(){
        super();
        setLocationRelativeTo(getParent());
        this.setMinimumSize(new Dimension(250,300));
        this.setMaximumSize(new Dimension(250,300));
        JTable leaderBoardTable;
        MyTableModel tableModel;
        File file = new File("src/leaderBoard.txt");
        JScrollPane jsp;
        if(!file.exists()){
            tableModel = new MyTableModel();
            tableModel.addColumn("Name");
            tableModel.addColumn("Score");
            leaderBoardTable = new JTable(tableModel);
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
            leaderBoardTable.setRowSorter(sorter);
            jsp = new JScrollPane(leaderBoardTable);
            jsp.setBounds(0,0,300,500);
            add(jsp);
            ObjectOutputStream oos;
            try{
                oos = new ObjectOutputStream(new FileOutputStream("src/leaderBoard.txt"));
                oos.writeObject(leaderBoardTable);
                oos.close();
            }catch(IOException ioe){
                System.out.print(ioe.toString() + " in LeaderBoard constructor\n"  + ioe.getMessage());
                ioe.printStackTrace();
            }
        }else{
            ObjectInputStream ois;
            try{
                ois = new ObjectInputStream(new FileInputStream("src/leaderBoard.txt"));
                leaderBoardTable = (JTable)ois.readObject();
                ois.close();
                jsp = new JScrollPane(leaderBoardTable);
                jsp.setBounds(0,0,250,300);
                add(jsp);
            }catch(IOException ioe){
                System.out.print(ioe.toString() + " in LeaderBoard constructor\n"  + ioe.getMessage());
                ioe.printStackTrace();
            }catch(ClassNotFoundException cnfe){
                System.out.print(cnfe.toString() + " in LeaderBoard constructor\n"  + cnfe.getMessage());
                cnfe.printStackTrace();
            }
        }
    }
    class MyTableModel extends DefaultTableModel{
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    }
}

