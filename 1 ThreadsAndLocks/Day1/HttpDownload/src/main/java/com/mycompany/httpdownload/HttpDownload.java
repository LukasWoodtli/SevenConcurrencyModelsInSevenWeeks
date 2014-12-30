/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.httpdownload;

import java.net.URL;

/**
 *
 * @author Boot
 */
public class HttpDownload {
    public static void main(String[] args) throws Exception {
        URL from = new URL("http://download.wikimedia.org/enwiki/latest/enwiki-latest-pages-articles.xml.bz2");
        Downloader downloader = new Downloader(from, "DownloadOut.txt");
        downloader.start();
        downloader.addListener(new ProgressListener() {
                                public void onProgress(int n) {System.out.print("\r"+n); System.out.flush();}
                                public void onComplete(boolean success) {}
        });
        downloader.join();
    }
}
