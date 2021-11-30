package co.diro.wing.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FTPUtil {

  public FTPUtil() {

  }

  /**
   * SFTP Upload -> 게시물 원본 http://javacpro.tistory.com/22 참고
   */
  @SuppressWarnings("finally")
  public int sftpUpload(String server, String id, String pw, int port, String keyfile, String modelPath, File uploadFile ) {
    
	int result = 0;

    Session session = null;
    Channel channel = null;
    ChannelSftp channelSftp = null;

    // SFTP 서버연결
    // String server = "192.168.1.17";
    // String user = "root";
    // String password = "0000";
    // int port = 22;
    // JSch 객체 생성
    JSch jsch = new JSch();
    try {
      // 세션객체 생성 ( user , host, port )
      String privateKey = keyfile;
      if (!"".equals(keyfile)) {
        jsch.addIdentity(privateKey);
      }
      session = jsch.getSession(id, server, port);
      session.setPassword(pw);
      
      System.out.println("=====================================> ftputil.java 1");

      // 세션관련 설정정보 설정
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no"); // 호스트 정보 검사하지 않는다.
      session.setConfig(config);
      session.connect();
      
      System.out.println("=====================================> ftputil.java 2");



      // sftp 채널 접속
      channel = session.openChannel("sftp");
      channel.connect();
      
      System.out.println("=====================================> ftputil.java 2");

    } catch (JSchException e) {
      System.out.println(e.toString());
      log.debug("[ERROR ERROR ERROR 위치데이터업로드] " + e.toString()); //FTP 연결 오류 날 수 있음.
    }

    channelSftp = (ChannelSftp) channel;
    FileInputStream in = null;
    try {

      // upload("./dat/", new File("C:\\Users\\jhkim1981\\Desktop\\20181102_TEST_UPLOAD.txt"));
      // 파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put
      in = new FileInputStream(uploadFile);
      channelSftp.cd(modelPath);
      channelSftp.put(in, uploadFile.getName());
    } catch (SftpException e) {
      System.out.println(e.toString());
    } catch (FileNotFoundException e) {
      System.out.println(e.toString());
    } finally {
      try {
        in.close();
        result = 1;
        channelSftp.disconnect();
      } catch (IOException e) {
        System.out.println(e.toString());
      }

      return result;
    }
  }

}