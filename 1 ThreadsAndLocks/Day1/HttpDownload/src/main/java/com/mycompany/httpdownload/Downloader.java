/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.httpdownload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;


/**
 *
 * @author Boot
 */
public class Downloader extends Thread {
    private InputStream in;
    private OutputStream out;
    private ArrayList<ProgressListener> listeners;
    
    public Downloader(URL url, String outputFilename) throws IOException {
        in  = url.openConnection().getInputStream();
        out = new FileOutputStream(outputFilename);
        listeners = new ArrayList<ProgressListener>();
    }
    
    public synchronized void addListener(ProgressListener listener) {
        listeners.add(listener);
    }
    
    public synchronized void removeListener(ProgressListener listener) {
        listeners.remove(listener);
    }
    
    public synchronized void updateProgress(int n) {
        for (ProgressListener listener: listeners) {
            listener.onProgress(n);
        }
    }
    
    public void run() {
        int n = 0;
        int total = 0;
        byte[] buffer = new byte[1024];
        
        try {
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
                total += n;
                updateProgress(total);
            }
            out.flush();
        } catch (IOException e) {}
    }
}
