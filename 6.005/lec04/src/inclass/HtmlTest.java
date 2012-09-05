package inclass;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

public class HtmlTest {
    // Testing strategy:
    //   l.size: 0, 1, n
    //   contents: no tags, one tag, all tags
    //   position: tag at start, tag in middle, tag at end
    //   element: <foo>, </foo>, word, empty string

    @Test
    public void emptyList() {
        ArrayList<String> l = new ArrayList<String>();
        Html.stripTags(l);
        assertEquals(new ArrayList<Integer>(), l);
    }

    @Test
    public void noTagsOneWord() {
        ArrayList<String> l = new ArrayList<String>(
                Arrays.asList(new String[] {"hello"}));
        Html.stripTags(l);
        assertEquals(new ArrayList<String>(
                        Arrays.asList(new String[] {"hello"})), 
                     l);
    }

    @Test
    public void noTagsSeveralWords() {
        ArrayList<String> l = new ArrayList<String>(
                Arrays.asList(new String[] {"now", "is", "", "time"}));
        Html.stripTags(l);
        assertEquals(new ArrayList<String>(
                        Arrays.asList(new String[] {"now", "is", "", "time"})), 
                     l);
    }

    @Test
    public void oneTagSeveralWords() {
        ArrayList<String> l = new ArrayList<String>(
                Arrays.asList(new String[] {"now", "<img>", "time"}));
        Html.stripTags(l);
        assertEquals(new ArrayList<String>(
                        Arrays.asList(new String[] {"now", "time"})), 
                     l);
    }
    
    @Test
    public void allTags() {
        ArrayList<String> l = new ArrayList<String>(
                Arrays.asList(new String[] {"<b>", "<a>", "</a>"}));
        Html.stripTags(l);
        System.out.println(l);
        assertEquals(new ArrayList<Integer>(), l);
    }
}
