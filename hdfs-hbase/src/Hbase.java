import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.google.protobuf.ServiceException;


public class Hbase {
	public static void main(String[] args) throws IOException, ServiceException {


        System.out.println("Hello: ");



        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "123.207.51.32");
        config.set("hbase.master", "123.207.51.32:60000");
        config.set("hbase.zookeeper.property.clientport", "123.207.51.32:2181");
        
        HBaseAdmin.checkHBaseAvailable(config);
		HTable table = new HTable(config, "hoolai-bi-hbase-test");
        
        
        Put put = new Put(Bytes.toBytes("row1"));
        put.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val1"));
        put.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual2"),Bytes.toBytes("val2"));

        table.put(put);
        table.close();
}
	
	
	
	
}
