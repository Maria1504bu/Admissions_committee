package view.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class SubjectTag extends TagSupport {
    private String index;
    private String name;
    private String maxGrade;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(String maxGrade) {
        this.maxGrade = maxGrade;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        try {
            out.print("<tr>");
            out.print(String.format("<td>%s</td>", index));
            out.print(String.format("<td>%s</td>", name));
            out.print(String.format("<td class=\"td-to-align\">%s</td>", maxGrade));
            out.print(String.format("<td class=\"td-to-align\"><input type=\"radio\" class=\"rgSubjects\" name=\"rgSubjects\" value=\"%s\"/>\n" +
                    "                        </td>", index));
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }


}