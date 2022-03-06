package ex01.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel {
    Object[][] data;
    String [] columnName;
    int rows,cols;//레코드 행과 열의 개수를 저장하는 변수

    @Override
    public int getColumnCount() {//필드의 개수
        return columnName.length;
    }

    @Override
    public int getRowCount() {//레코드 개수
        return data.length; 
    }

    public int getRowCount(ResultSet rsScroll) {//레코드 개수
        try {
            rsScroll.last(); //레코드의 마지막으로 이동.
            rows = rsScroll.getRow();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return rows;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    public void setData(ResultSet rs){
        try {
            String title;
            ResultSetMetaData rsmd = rs.getMetaData();
            cols = rsmd.getColumnCount(); //열의 갯수 얻어옴
            columnName = new String[cols];

            for (int i = 0; i < cols; i++) {
                columnName[i] = rsmd.getColumnName(i+1); // 필드 번호는 1부터 시작
            }

            data = new Object[rows][cols];
            int r = 0;

            while (rs.next()){
                for (int i = 0; i < cols; i++) {

                    if(i != 1){
                        data[r][i] = rs.getObject(i + 1);
                    }else {
                        data[r][i] = rs.getObject(i + 1);
                    }
                }//for end
                r++;
            }//while end
        } catch (Exception e) {e.printStackTrace();}
    }
}
