/**
 *
 */
package com.metaship.edu.utils.lang;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Properties;

/**
 * @author guohuanmeng
 */
public class ResourceUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

    public static InputStream loadInputStreamFromClassPath(String filePath) throws Exception {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
    }

    public static Properties loadPropertiesFromClassPath(String filePath) throws Exception {
        Properties pc = new Properties();
        pc.load(loadInputStreamFromClassPath(filePath));
        return pc;
    }

    public static void appendContent(String fileName, String content) {
        if (logger.isDebugEnabled()) {
            logger.debug("向文件：" + fileName + "中追加内容：" + content);
        }
        RandomAccessFile randomFile = null;
        try {
            File file = new File(fileName);
            File pFile = file.getParentFile();
            if (!pFile.exists()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("文件目录：" + pFile.getPath() + "不存在，开始创建该目录。");
                }
                pFile.mkdirs();
            }
            content = content + "\r\n";
            randomFile = new RandomAccessFile(fileName, "rw");

            long fileLength = randomFile.length();

            randomFile.seek(fileLength);
            content = new String(content.getBytes("UTF-8"), "ISO8859_1");
            randomFile.writeBytes(content);
        } catch (IOException e) {
            logger.error("向文件：" + fileName + "中追加内容：" + content + "出错", e);
        } finally {
            if (randomFile != null)
                try {
                    randomFile.close();
                } catch (IOException e) {
                    logger.error("流关闭出错", e);
                }
        }
    }

    public static void writeContent(String fileName, String content) {
        if (logger.isDebugEnabled()) {
            logger.debug("重新想文件：" + fileName + "中写入内容：" + content);
        }

        FileWriter fw = null;
        try {
            File file = new File(fileName);
            File pFile = file.getParentFile();
            if (!pFile.exists()) {
                pFile.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            fw = new FileWriter(file);
            fw.write(content, 0, content.length());
            fw.flush();
        } catch (IOException e) {
            logger.error("向文件：" + fileName + "中写入内容：" + content + "出错", e);
        } finally {
            if (fw != null)
                try {
                    fw.close();
                } catch (IOException e) {
                    logger.error("流关闭出错", e);
                }
        }
    }

    public static byte[] readStream(InputStream inputStream) {
        ByteArrayOutputStream outputSteam = null;
        try {
            outputSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputSteam.write(buffer, 0, len);
            }
            byte[] arrayOfByte1 = outputSteam.toByteArray();

            return arrayOfByte1;
        } catch (Exception e) {
            logger.error("将输入流转为byte数组出错。", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputSteam != null)
                    outputSteam.close();
            } catch (IOException e) {
                logger.error("流关闭出错", e);
            }
        }
        return null;
    }

    /**
     * 读取文件内容。
     * <p>
     * <b>注意：</b><br>
     * </p>
     * <p>
     * <b>变更记录：</b><br>
     * </p>
     *
     * @param fileName
     * @return
     * @author: guohm
     * @date:2015年1月1日 下午5:09:56
     * @since 1.0.0
     */
    public static String readFile(String fileName) {
        return readFile(new File(fileName));
    }

    /**
     * 读取文件内容。
     * <p>
     * <b>注意：</b><br>
     * </p>
     * <p>
     * <b>变更记录：</b><br>
     * </p>
     *
     * @param file
     * @return
     * @author: guohm
     * @date:2015年1月1日 下午5:09:56
     * @since 1.0.0
     */
    public static String readFile(File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] data = readStream(inputStream);
            if (data == null || data.length == 0) {
                return null;
            }
            String content = new String(data, "UTF-8");
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String readFileFromClass(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = loadInputStreamFromClassPath(fileName);

            byte[] data = readStream(inputStream);
            if ((data == null) || (data.length == 0)) {
                return null;
            }
            String content = new String(data, "UTF-8");
            return content;
        } catch (Exception e) {
            logger.error("readAll 异常：" + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("流关闭出错", e);
                }
            }
        }
        return null;
    }

    public static String readLine(String fileName, int line) {
        RandomAccessFile raf = null;
        try {
            StringBuffer result = new StringBuffer();
            raf = new RandomAccessFile(fileName, "rw");
            int i = 1;
            String str = null;
            if ((i++ == line) && ((str = raf.readLine()) != null)) {
                result.append(new String(str.getBytes("ISO8859_1"), "UTF-8"));
            }

            String str1 = result.toString();

            return str1;
        } catch (IOException e) {
            logger.error("读取文件内容出错。", e);
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    logger.error("流关闭出错", e);
                }
            }
        }
        return null;
    }

    public static String readLine(String fileName) {
        /*FileWriter fw = null;
		try {
			StringBuffer result = new StringBuffer();
			fw = new FileWriter();
			int i = 1;
			String str = null;
			if ((i++ == line) && ((str = raf.readLine()) != null)) {
				result.append(new String(str.getBytes("ISO8859_1"), "UTF-8"));
			}

			String str1 = result.toString();

			return str1;
		} catch (IOException e) {
			logger.error("读取文件内容出错。", e);
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					logger.error("流关闭出错", e);
				}
			}
		}*/
        return null;
    }

    public static File getFileByPath(String path) {
        try {
            if (StringUtils.isBlank(path)) {
                return null;
            }
            File file = new File(path);
            return file;
        } catch (Exception e) {
            logger.error("获取文件出错，文件路径为：" + path, e);
        }
        return null;
    }

    public static boolean saveFile(String path, String fileName, File file) {
        boolean flag = false;
        try {
            File newsFileRoot = new File(path);
            if (!newsFileRoot.exists()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("文件目录：" + path + "不存在，开始创建文件目录...");
                }
                newsFileRoot.mkdirs();
            }
            String fullFileName = null;
            if (!path.endsWith(File.pathSeparator))
                fullFileName = path + File.pathSeparator + fileName;
            else {
                fullFileName = path + fileName;
            }
            FileOutputStream fos = null;
            FileInputStream fis = null;
            try {
                fos = new FileOutputStream(fullFileName);
                fis = new FileInputStream(file);
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = fis.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                flag = true;
                boolean bool1 = flag;

                return bool1;
            } catch (IOException e) {
                logger.error("文件保存出错", e);
            } finally {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null)
                    fos.close();
            }
        } catch (Exception ex) {
            logger.error("文件保存出错", ex);
        }
        return flag;
    }

    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);

        if ((file.isFile()) && (file.exists())) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean deleteFolder(String filePath) {
        boolean flag = false;
        File file = new File(filePath);

        if (!file.exists()) {
            return flag;
        }

        if (file.isFile()) {
            return deleteFile(filePath);
        }
        return deleteDirectory(filePath);
    }

    public static boolean deleteDirectory(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            if (logger.isDebugEnabled()) {
                logger.debug("filePath为空。");
            }
            return false;
        }
        boolean flag = false;

        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);

        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        flag = true;

        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (flag)
                    continue;
                break;
            }

            flag = deleteDirectory(files[i].getAbsolutePath());
            if (!flag) {
                break;
            }
        }
        if (!flag) {
            return false;
        }

        return dirFile.delete();
    }

    public static boolean saveBufferedImage(BufferedImage bufferedImage, String path, String imageName) {
        try {
            Image big = bufferedImage.getScaledInstance(256, 256, 1);

            BufferedImage inputbig = new BufferedImage(256, 256, 4);

            inputbig.getGraphics().drawImage(bufferedImage, 0, 0, 256, 256, null);

            File pathfile = new File(path);
            if (pathfile.exists()) {
                logger.info("saveBySecurityImage [" + path + "]存在");
            } else {
                pathfile.mkdirs();
            }
            ImageIO.write(inputbig, "jpg", new File(path + "/" + imageName));
            return true;
        } catch (Exception e) {
            logger.error("saveBySecurityImage 异常：" + e.getMessage());
        }
        return false;
    }

    public static void copyFile(File src, File dest) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(src);
            fo = new FileOutputStream(dest);
            in = fi.getChannel();
            out = fo.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                buffer.clear();

                int i = in.read(buffer);
                if (i == -1) {
                    break;
                }

                buffer.flip();
                out.write(buffer);
            }
        } catch (IOException e) {
            logger.error("文件拷贝出错", e);
        } finally {
            try {
                if (fi != null) {
                    fi.close();
                }
                if (in != null) {
                    in.close();
                }
                if (fo != null) {
                    fo.close();
                }
                if (out != null)
                    out.close();
            } catch (IOException e) {
                logger.error("流关闭时出错", e);
            }
        }
    }

    public static void writeFile(File file, String content) {
        FileOutputStream fo = null;
        FileChannel out = null;
        try {
            fo = new FileOutputStream(file);
            out = fo.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            byte[] bytes = content.getBytes();
            for (int i = 0; i < bytes.length; ++i) {
                buffer.put(bytes[i]);
            }
            buffer.flip();

            out.write(buffer);
        } catch (IOException e) {
            logger.error("文件拷贝出错", e);
        } finally {
            try {
                if (fo != null) {
                    fo.close();
                }
                if (out != null)
                    out.close();
            } catch (IOException e) {
                logger.error("流关闭时出错", e);
            }
        }
    }

    public static void writeFile(String filePath, String content) {
        File file = new File(filePath);
        File pFile = file.getParentFile();
        if (!pFile.exists()) {
            pFile.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }

        writeFile(file, content);
    }

    public static void writeClasspathFile(String fileClassPath, String content) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(fileClassPath);
        if (resource == null) {
            return;
        }
        String sysFileName = resource.getFile();
        File file = new File(sysFileName);
        File pFile = file.getParentFile();
        if (!pFile.exists()) {
            pFile.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }

        writeFile(file, content);
    }

    public static void main(String[] args) {
        writeClasspathFile("a.txt", "中国馆22ssds2");
    }
}
