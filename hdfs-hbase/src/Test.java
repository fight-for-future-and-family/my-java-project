import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
 
 
public class Test {
 
    private static Configuration conf = null;
    /**
     * Initialization
     */
    static {
        conf = HBaseConfiguration.create();
//        conf.set("hadoop.security.authentication", "Kerberos");
        conf.set("hbase.zookeeper.quorum", "123.207.51.32");
        conf.set("hbase.master", "123.207.51.32:60000");
        conf.set("hbase.zookeeper.property.clientport", "123.207.51.32:2181");
    }
 
 
 
    /**
     * Create a table
     */
    public static void creatTable(String tableName, String[] familys) throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (admin.tableExists(tableName)) {
            System.out.println("table already exists!");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            for (int i = 0; i < familys.length; i++) {
                tableDesc.addFamily(new HColumnDescriptor(familys[i]));
            }
            admin.createTable(tableDesc);
            System.out.println("create table " + tableName + " ok.");
        }
    }
 
    /**
     * Delete a table
     */
    public static void deleteTable(String tableName) throws Exception {
        try {
            HBaseAdmin admin = new HBaseAdmin(conf);
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("delete table " + tableName + " ok.");
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Put (or insert) a row
     */
    public static void addRecord(String tableName, String rowKey,
            String family, String qualifier, String value) throws Exception {
        try {
            HTable table = new HTable(conf, tableName);
            Put put = new Put(Bytes.toBytes(rowKey));
            put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes
                    .toBytes(value));
            table.put(put);
            System.out.println("insert recored " + rowKey + " to table "
                    + tableName + " ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Delete a row
     */
    public static void delRecord(String tableName, String rowKey)
            throws IOException {
        HTable table = new HTable(conf, tableName);
        List<Delete> list = new ArrayList<Delete>();
        Delete del = new Delete(rowKey.getBytes());
        list.add(del);
        table.delete(list);
        System.out.println("del recored " + rowKey + " ok.");
    }
 
    /**
     * Get a row
     */
    public static void getOneRecord (String tableName, String rowKey) throws IOException{
        HTable table = new HTable(conf, tableName);
        Get get = new Get(rowKey.getBytes());
        Result rs = table.get(get);
        for(KeyValue kv : rs.raw()){
            System.out.print(new String(kv.getRow()) + " " );
            System.out.print(new String(kv.getFamily()) + ":" );
            System.out.print(new String(kv.getQualifier()) + " " );
            System.out.print(kv.getTimestamp() + " " );
            System.out.println(new String(kv.getValue()));
        }
    }
    /**
     * Scan (or list) a table
     */
    public static void getAllRecord (String tableName) {
        try{
             HTable table = new HTable(conf, tableName);
             Scan s = new Scan();
             ResultScanner ss = table.getScanner(s);
             for(Result r:ss){
                 for(KeyValue kv : r.raw()){
                    System.out.print(new String(kv.getRow()) + " ");
                    System.out.print(new String(kv.getFamily()) + ":");
                    System.out.print(new String(kv.getQualifier()) + " ");
                    System.out.print(kv.getTimestamp() + " ");
                    System.out.println(new String(kv.getValue()));
                 }
             }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
 
    public static void main(String[] agrs) {
        try {
            String tablename = "scores";
            String[] familys = { "grade", "course" };
            
            
//            if (!UserGroupInformation.isSecurityEnabled()) throw new IOException("Security is not enabled in core-site.xml");
        try {
            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation userGroupInformation = UserGroupInformation.loginUserFromKeytabAndReturnUGI("hbase-app@KRB.HDP", "/home/hbase-app/hbase-app.headless.keytab" );
            UserGroupInformation.setLoginUser(userGroupInformation);
        } catch(IOException e) {
        e.printStackTrace();
        }
         
        Test.creatTable(tablename, familys);
 
            // add record zkb
            Test.addRecord(tablename, "zkb", "grade", "", "5");
            Test.addRecord(tablename, "zkb", "course", "", "90");
            Test.addRecord(tablename, "zkb", "course", "math", "97");
            Test.addRecord(tablename, "zkb", "course", "art", "87");
            // add record baoniu
            Test.addRecord(tablename, "baoniu", "grade", "", "4");
            Test.addRecord(tablename, "baoniu", "course", "math", "89");
 
            System.out.println("===========get one record========");
            Test.getOneRecord(tablename, "zkb");
 
            System.out.println("===========show all record========");
            Test.getAllRecord(tablename);
 
            System.out.println("===========del one record========");
            Test.delRecord(tablename, "baoniu");
            Test.getAllRecord(tablename);
 
            System.out.println("===========show all record========");
            Test.getAllRecord(tablename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}