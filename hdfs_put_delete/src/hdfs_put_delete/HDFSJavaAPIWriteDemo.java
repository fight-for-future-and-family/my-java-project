package hdfs_put_delete;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.analysis.function.Constant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

public class HDFSJavaAPIWriteDemo {
		
	 public static DistributedFileSystem dfs=null;
	    public static String nameNodeUri="hdfs://192.168.90.110:9000";
	    
	    public void initFileSystem() throws Exception{
	        System.out.println("初始化hadoop客户端");
	        //设置hadoop的登录用户名
	        System.setProperty("HADOOP_USER_NAME", "root");
	        dfs=new DistributedFileSystem();
	        dfs.initialize(new URI(nameNodeUri), new Configuration());
	        System.out.println("客户端连接成功");
	        Path workingDirectory = dfs.getWorkingDirectory();
	        System.out.println("工作目录："+workingDirectory);
	    }
	 
	    public void testFileList() throws Exception{
	        RemoteIterator<LocatedFileStatus> listFiles = dfs.listFiles(new Path("/user/hive/warehouse/wl.db/test_install/snid=200/gameid=276/ds=2017-06-10"), true);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        while (listFiles.hasNext()) {
	            LocatedFileStatus fileStatus = (LocatedFileStatus) listFiles.next();
	            //权限
	            FsPermission permission = fileStatus.getPermission();
	            //拥有者
	            String owner = fileStatus.getOwner();
	            //组
	            String group = fileStatus.getGroup();
	            //文件大小byte
	            long len = fileStatus.getLen();
	            long modificationTime = fileStatus.getModificationTime();
	            Path path = fileStatus.getPath();
	            System.out.println("-------------------------------");
	            System.out.println("permission:"+permission);
	            System.out.println("owner:"+owner);
	            System.out.println("group:"+group);
	            System.out.println("len:"+len);
	            System.out.println("modificationTime:"+sdf.format(new Date(modificationTime)));
	            System.out.println("path:"+path);
	        }
	    }
	    public void deleteFile() throws Exception{
	    	
	    	InputStream f = new FileInputStream("D:/工作相关/脚本/000000_0_wl_new/000000_0_wl_new");
	    	Path outFile = new Path("/user/hive/warehouse/wl.db/test_install/snid=200/gameid=276/ds=2017-06-10/000000_0_wl_new");
	    	FSDataOutputStream out = dfs.create(outFile);
	    	
	    	/*Class<?> codecClass = Class.forName(Constant.lzoClass);
	    	CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, new Configuration());
	    	CompressionOutputStream outt = codec.createOutputStream(out);*/
	    	IOUtils.copy(f, out);
	    	
	    	/*int bytesRead;
			byte[] buffer;
			while ((bytesRead = f.read()) > 0) {
	    		  out.write(bytesRead);
	    		}*/
			
			
			
	    	f.close();
	    	out.close();
//	    	dfs.delete(new Path("/user/hive/warehouse/wl.db/test_install/snid=200/gameid=276/ds=2017-06-10/000000_0_wl_new"));
	        RemoteIterator<LocatedFileStatus> listFiles = dfs.listFiles(new Path("/user/hive/warehouse/wl.db/test_install/snid=200/gameid=276/ds=2017-06-10"), true);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        while (listFiles.hasNext()) {
	            LocatedFileStatus fileStatus = (LocatedFileStatus) listFiles.next();
	            //权限
	            FsPermission permission = fileStatus.getPermission();
	            //拥有者
	            String owner = fileStatus.getOwner();
	            //组
	            String group = fileStatus.getGroup();
	            //文件大小byte
	            long len = fileStatus.getLen();
	            long modificationTime = fileStatus.getModificationTime();
	            Path path = fileStatus.getPath();
	            System.out.println("-------------------------------");
	            System.out.println("permission:"+permission);
	            System.out.println("owner:"+owner);
	            System.out.println("group:"+group);
	            System.out.println("len:"+len);
	            System.out.println("modificationTime:"+sdf.format(new Date(modificationTime)));
	            System.out.println("path:"+path);
	        }
	    }
		
		
		
	public static void main(String[] args) throws Exception{
		
		HDFSJavaAPIWriteDemo wl=new HDFSJavaAPIWriteDemo();
		wl.initFileSystem();
		//wl.testFileList();
		wl.deleteFile();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	/*	// Impersonates user "root" to avoid performance problems. You should replace it 
		// with user that you are running your HDFS cluster with
		System.setProperty("HADOOP_USER_NAME", "root");
		
		// Path that we need to create in HDFS. Just like Unix/Linux file systems, HDFS file system starts with "/"
		final Path path = new Path("/user/hive/warehouse/wl.db/test_install/snid=200/gameid=276/ds=2017-06-10");
		
		// Uses try with resources in order to avoid close calls on resources
		// Creates anonymous sub class of DistributedFileSystem to allow calling initialize as DFS will not be usable otherwise
		try(
				final DistributedFileSystem dFS = new DistributedFileSystem() { 
					{
						initialize(new URI("hdfs://192.168.90.110:9000"), new Configuration());
					}
				}; 
				// Gets output stream for input path using DFS instance
				final FSDataOutputStream streamWriter = dFS.create(path);
				// Wraps output stream into PrintWriter to use high level and sophisticated methods
				final PrintWriter writer = new PrintWriter(streamWriter);) 
				
		{
			// Writes tutorials information to file using print writer
			writer.println("Getting Started with Apache Spark => http://www.allprogrammingtutorials.com/tutorials/getting-started-with-apache-spark.php");
			writer.println("Developing Java Applications in Apache Spark => http://www.allprogrammingtutorials.com/tutorials/developing-java-applications-in-spark.php");
			writer.println("Getting Started with RDDs in Apache Spark => http://www.allprogrammingtutorials.com/tutorials/getting-started-with-rdds-in-spark.php");
			
			System.out.println("File Written to HDFS successfully!");
		}*/
	}
}
