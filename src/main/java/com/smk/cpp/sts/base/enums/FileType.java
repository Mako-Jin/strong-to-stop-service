package com.smk.cpp.sts.base.enums;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月24日 22:20
 * @Description:
 */
public enum FileType {
    /**
     * 文件夹
     */
    DIRECTORY("", 1),
    TXT("txt", 2),
    PNG("png", 3),
    JPG("jpg", 3),
    ;
    
    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件类型
     */
    private int fileType;

    FileType(String fileSuffix, int fileType) {
        this.fileSuffix = fileSuffix;
        this.fileType = fileType;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
    
    // map.put("323", "text/h323");
    //     map.put("acx", "application/internet-property-stream");
    //     map.put("ai", "application/postscript");
    //     map.put("aif", "audio/x-aiff");
    //     map.put("aifc", "audio/x-aiff");
    //     map.put("aiff", "audio/x-aiff");
    //     map.put("asf", "video/x-ms-asf");
    //     map.put("asr", "video/x-ms-asf");
    //     map.put("asx", "video/x-ms-asf");
    //     map.put("au", "audio/basic");
    //     map.put("avi", "video/x-msvideo");
    //     map.put("axs", "application/olescript");
    //     map.put("bas", "text/plain");
    //     map.put("bcpio", "application/x-bcpio");
    //     map.put("bin", "application/octet-stream");
    //     map.put("bmp", "image/bmp");
    //     map.put("c", "text/plain");
    //     map.put("cat", "application/vnd.ms-pkiseccat");
    //     map.put("cdf", "application/x-cdf");
    //     map.put("cer", "application/x-x509-ca-cert");
    //     map.put("class", "application/octet-stream");
    //     map.put("clp", "application/x-msclip");
    //     map.put("cmx", "image/x-cmx");
    //     map.put("cod", "image/cis-cod");
    //     map.put("cpio", "application/x-cpio");
    //     map.put("crd", "application/x-mscardfile");
    //     map.put("crl", "application/pkix-crl");
    //     map.put("crt", "application/x-x509-ca-cert");
    //     map.put("csh", "application/x-csh");
    //     map.put("css", "text/css");
    //     map.put("dcr", "application/x-director");
    //     map.put("der", "application/x-x509-ca-cert");
    //     map.put("dir", "application/x-director");
    //     map.put("dll", "application/x-msdownload");
    //     map.put("dms", "application/octet-stream");
    //     map.put("doc", "application/msword");
    //     map.put("dot", "application/msword");
    //     map.put("dvi", "application/x-dvi");
    //     map.put("dxr", "application/x-director");
    //     map.put("eps", "application/postscript");
    //     map.put("etx", "text/x-setext");
    //     map.put("evy", "application/envoy");
    //     map.put("exe", "application/octet-stream");
    //     map.put("fif", "application/fractals");
    //     map.put("flr", "x-world/x-vrml");
    //     map.put("gif", "image/gif");
    //     map.put("gtar", "application/x-gtar");
    //     map.put("gz", "application/x-gzip");
    //     map.put("h", "text/plain");
    //     map.put("hdf", "application/x-hdf");
    //     map.put("hlp", "application/winhlp");
    //     map.put("hqx", "application/mac-binhex40");
    //     map.put("hta", "application/hta");
    //     map.put("htc", "text/x-component");
    //     map.put("htm", "text/plain");
    //     map.put("html", "text/plain");
    //     map.put("htt", "text/webviewhtml");
    //     map.put("ico", "image/x-icon");
    //     map.put("ief", "image/ief");
    //     map.put("iii", "application/x-iphone");
    //     map.put("ins", "application/x-internet-signup");
    //     map.put("isp", "application/x-internet-signup");
    //     map.put("jfif", "image/pipeg");
    //     map.put("jpe", "image/jpeg");
    //     map.put("jpeg", "image/jpeg");
    //     map.put("jpg", "image/jpeg");
    //     map.put("js", "application/x-javascript");
    //     map.put("latex", "application/x-latex");
    //     map.put("lha", "application/octet-stream");
    //     map.put("lsf", "video/x-la-asf");
    //     map.put("lsx", "video/x-la-asf");
    //     map.put("lzh", "application/octet-stream");
    //     map.put("m13", "application/x-msmediaview");
    //     map.put("m14", "application/x-msmediaview");
    //     map.put("m3u", "audio/x-mpegurl");
    //     map.put("man", "application/x-troff-man");
    //     map.put("mdb", "application/x-msaccess");
    //     map.put("me", "application/x-troff-me");
    //     map.put("mht", "message/rfc822");
    //     map.put("mhtml", "message/rfc822");
    //     map.put("mid", "audio/mid");
    //     map.put("mny", "application/x-msmoney");
    //     map.put("mov", "video/quicktime");
    //     map.put("movie", "video/x-sgi-movie");
    //     map.put("mp2", "video/mpeg");
    //     map.put("mp3", "audio/mpeg");
    //     map.put("mp4", "video/mp4");
    //     map.put("mpa", "video/mpeg");
    //     map.put("mpe", "video/mpeg");
    //     map.put("mpeg", "video/mpeg");
    //     map.put("mpg", "video/mpeg");
    //     map.put("mpp", "application/vnd.ms-project");
    //     map.put("mpv2", "video/mpeg");
    //     map.put("ms", "application/x-troff-ms");
    //     map.put("mvb", "application/x-msmediaview");
    //     map.put("nws", "message/rfc822");
    //     map.put("oda", "application/oda");
    //     map.put("p10", "application/pkcs10");
    //     map.put("p12", "application/x-pkcs12");
    //     map.put("p7b", "application/x-pkcs7-certificates");
    //     map.put("p7c", "application/x-pkcs7-mime");
    //     map.put("p7m", "application/x-pkcs7-mime");
    //     map.put("p7r", "application/x-pkcs7-certreqresp");
    //     map.put("p7s", "application/x-pkcs7-signature");
    //     map.put("pbm", "image/x-portable-bitmap");
    //     map.put("pdf", "application/pdf");
    //     map.put("pfx", "application/x-pkcs12");
    //     map.put("pgm", "image/x-portable-graymap");
    //     map.put("pko", "application/ynd.ms-pkipko");
    //     map.put("pma", "application/x-perfmon");
    //     map.put("pmc", "application/x-perfmon");
    //     map.put("pml", "application/x-perfmon");
    //     map.put("pmr", "application/x-perfmon");
    //     map.put("pmw", "application/x-perfmon");
    //     map.put("pnm", "image/x-portable-anymap");
    //     map.put("pot,", "application/vnd.ms-powerpoint");
    //     map.put("ppm", "image/x-portable-pixmap");
    //     map.put("pps", "application/vnd.ms-powerpoint");
    //     map.put("ppt", "application/vnd.ms-powerpoint");
    //     map.put("prf", "application/pics-rules");
    //     map.put("ps", "application/postscript");
    //     map.put("pub", "application/x-mspublisher");
    //     map.put("qt", "video/quicktime");
    //     map.put("ra", "audio/x-pn-realaudio");
    //     map.put("ram", "audio/x-pn-realaudio");
    //     map.put("ras", "image/x-cmu-raster");
    //     map.put("rgb", "image/x-rgb");
    //     map.put("rmi", "audio/mid");
    //     map.put("roff", "application/x-troff");
    //     map.put("rtf", "application/rtf");
    //     map.put("rtx", "text/richtext");
    //     map.put("scd", "application/x-msschedule");
    //     map.put("sct", "text/scriptlet");
    //     map.put("setpay", "application/set-payment-initiation");
    //     map.put("setreg", "application/set-registration-initiation");
    //     map.put("sh", "application/x-sh");
    //     map.put("shar", "application/x-shar");
    //     map.put("sit", "application/x-stuffit");
    //     map.put("snd", "audio/basic");
    //     map.put("spc", "application/x-pkcs7-certificates");
    //     map.put("spl", "application/futuresplash");
    //     map.put("src", "application/x-wais-source");
    //     map.put("sst", "application/vnd.ms-pkicertstore");
    //     map.put("stl", "application/vnd.ms-pkistl");
    //     map.put("stm", "text/html");
    //     map.put("svg", "image/svg+xml");
    //     map.put("sv4cpio", "application/x-sv4cpio");
    //     map.put("sv4crc", "application/x-sv4crc");
    //     map.put("swf", "application/x-shockwave-flash");
    //     map.put("t", "application/x-troff");
    //     map.put("tar", "application/x-tar");
    //     map.put("tcl", "application/x-tcl");
    //     map.put("tex", "application/x-tex");
    //     map.put("texi", "application/x-texinfo");
    //     map.put("texinfo", "application/x-texinfo");
    //     map.put("tgz", "application/x-compressed");
    //     map.put("tif", "image/tiff");
    //     map.put("tiff", "image/tiff");
    //     map.put("tr", "application/x-troff");
    //     map.put("trm", "application/x-msterminal");
    //     map.put("tsv", "text/tab-separated-values");
    //     map.put("txt", "text/plain");
    //     map.put("uls", "text/iuls");
    //     map.put("ustar", "application/x-ustar");
    //     map.put("vcf", "text/x-vcard");
    //     map.put("vrml", "x-world/x-vrml");
    //     map.put("wav", "audio/x-wav");
    //     map.put("wcm", "application/vnd.ms-works");
    //     map.put("wdb", "application/vnd.ms-works");
    //     map.put("wks", "application/vnd.ms-works");
    //     map.put("wmf", "application/x-msmetafile");
    //     map.put("wps", "application/vnd.ms-works");
    //     map.put("wri", "application/x-mswrite");
    //     map.put("wrl", "x-world/x-vrml");
    //     map.put("wrz", "x-world/x-vrml");
    //     map.put("xaf", "x-world/x-vrml");
    //     map.put("xbm", "image/x-xbitmap");
    //     map.put("xla", "application/vnd.ms-excel");
    //     map.put("xlc", "application/vnd.ms-excel");
    //     map.put("xlm", "application/vnd.ms-excel");
    //     map.put("xls", "application/vnd.ms-excel");
    //     map.put("xlt", "application/vnd.ms-excel");
    //     map.put("xlw", "application/vnd.ms-excel");
    //     map.put("xof", "x-world/x-vrml");
    //     map.put("xpm", "image/x-xpixmap");
    //     map.put("xwd", "image/x-xwindowdump");
    //     map.put("zip", "application/zip");
    //     map.put("z", "application/x-compress");
}
