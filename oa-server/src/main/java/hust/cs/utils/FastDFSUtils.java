package hust.cs.utils;

import lombok.extern.slf4j.Slf4j;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ClassName: FastDFSUtils
 * PackageName:hust.cs.server.utils
 * Description: fdfs工具类
 * date: 2022/3/30 15:16
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Slf4j(topic = "FastDFSUtils")
public class FastDFSUtils {

    static {
        //初始化客户端
        try {
            String path = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(path);
        } catch (Exception e) {
            log.error("初始化失败,{}"+e);
        }
    }

    /***
     * @author Sunny
     * @description
     * @createTime  2022/3/30 15:38
     * @param file 上传文件
     * @return java.lang.String[]
     **/
    public static String[] upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        log.info("文件名："+originalFilename);
        StorageClient storageClient = null;
        String[] upload_results = null;
        try {
            //获取存储客户端
            storageClient = getStorageClient();
            //上传信息
            upload_results = storageClient.upload_file(file.getBytes()
                    , originalFilename.substring(originalFilename.lastIndexOf(".") + 1), null);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("上传失败, {}",e.getMessage());
        }
        if(null==upload_results){
            log.error("上传失败");
        }
        return upload_results;
    }

    /***
     * @author Sunny
     * @description 获取文件信息
     * @createTime  2022/3/30 15:42
     * @param groupName, remoteFileName
     * @return org.csource.fastdfs.FileInfo
     **/
    public static FileInfo getFileInfo(String groupName,String remoteFileName){
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            FileInfo file_info = storageClient.get_file_info(groupName, remoteFileName);
            return file_info;
        } catch (Exception e) {
            log.error("文件信息获取失败",e.getMessage());
        }
        return null;
    }

    /***
     * @author Sunny
     * @description 下载文件
     * @createTime  2022/3/30 15:43
     * @param
     * @return
     **/
    public static InputStream downloadFilename(String groupName,String remoteFileName){
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            byte[] fileBytes = storageClient.download_file(groupName, remoteFileName);
            InputStream in = new ByteArrayInputStream(fileBytes);
            return in;
        } catch (Exception e) {
            log.error("文件下载失败+",e.getMessage());
        }
        return null;
    }

    /***
     * @author Sunny
     * @description 删除文件
     * @createTime  2022/3/30 15:47
     * @param groupName, remoteFileName
     * @return void
     **/
    public static void deleteFile(String groupName,String remoteFileName){
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            storageClient.delete_file(groupName, remoteFileName);
        } catch (Exception e) {
            log.error("文件信息获取失败",e.getMessage());
        }
    }

    public static String getTrackerUrl(){
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        try {
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            return "http://"+storageServer.getInetSocketAddress().getHostString()+":8888/";
        } catch (IOException e) {
            log.error("文件路径获取失败");
        }
        return null;
    }

    /***
     * @author Sunny
     * @description 得到文件客户端
     * @createTime  2022/3/30 15:29
     * @param
     * @return org.csource.fastdfs.StorageClient
     **/
    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }

    /***
     * @author Sunny
     * @description 生成tracker服务器
     * @createTime  2022/3/30 15:27
     * @param
     * @return org.csource.fastdfs.TrackerServer
     **/
    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer connection = trackerClient.getConnection();
        return connection;
    }
}
