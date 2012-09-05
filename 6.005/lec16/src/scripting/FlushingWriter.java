package scripting;
import java.io.IOException;
import java.io.Writer;


class FlushingWriter extends Writer {
    private final Writer w;
    
    public FlushingWriter(Writer w) {
        this.w = w;
    }

    public Writer append(char c) throws IOException {
        try {
            return w.append(c);
        } finally {
            w.flush();
        }
    }

    public Writer append(CharSequence csq, int start, int end)
            throws IOException {
        try {
            return w.append(csq, start, end);
        } finally {
            w.flush();
        }
    }

    public Writer append(CharSequence csq) throws IOException {
        try {
            return w.append(csq);
        } finally {
            w.flush();
        }
    }

    public void close() throws IOException {
        w.close();
    }
    
    public void flush() throws IOException {
        w.flush();
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        try {
            w.write(cbuf, off, len);
        } finally {
            w.flush();
        }
    }

    public void write(char[] cbuf) throws IOException {
        try {
            w.write(cbuf);
        } finally {
            w.flush();
        }
    }

    public void write(int c) throws IOException {
        try {
            w.write(c);
        } finally {
            w.flush();
        }
    }

    public void write(String str, int off, int len) throws IOException {
        try {
            w.write(str, off, len);
        } finally {
            w.flush();
        }
    }

    public void write(String str) throws IOException {
        try {
            w.write(str);
        } finally {
            w.flush();
        }
    }
    
}