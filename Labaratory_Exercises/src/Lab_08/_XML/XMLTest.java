package Lab_08._XML;

import java.util.*;

interface XMLComponent {
    void addAttribute(String attribute, String value);
    void addComponent(XMLComponent component);
}

class XMLLeaf implements XMLComponent {

    private Map<String, String> attributes;
    private String value, tag;

    public XMLLeaf(String tag, String value) {
        this.value = value;
        this.tag = tag;
        this.attributes = new LinkedHashMap<>();
    }

    @Override
    public void addAttribute(String attribute, String value) {
        this.attributes.putIfAbsent(attribute, value);
    }

    @Override
    public void addComponent(XMLComponent component) {

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<").append(tag).append(" ");
        for(Map.Entry entry : attributes.entrySet())
            stringBuilder.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\" ");

        stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));
        stringBuilder.append(">").append(value).append("</").append(tag).append(">");
        return stringBuilder.toString().toString();
    }
}

class XMLComposite implements XMLComponent {

    private Map<String, String> attributes;
    private List<XMLComponent> components;
    private String tag;

    public XMLComposite(String tag) {
        this.tag = tag;
        this.attributes = new LinkedHashMap<>();
        this.components = new ArrayList<>();
    }

    public List<XMLComponent> getComponents() {
        return components;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public void addAttribute(String attribute, String value) {
        this.attributes.putIfAbsent(attribute, value);
    }

    public String getTag() {
        return tag;
    }

    @Override
    public void addComponent(XMLComponent component) {
        this.components.add(component);
    }

    public static String printAttributes(Map<String, String> attrList) {
        StringBuilder stringBuilder = new StringBuilder();
        attrList.forEach((key, value) -> stringBuilder.append(key).append("=\"")
                .append(value).append("\" "));
        return stringBuilder.substring(0, stringBuilder.length()-1);
    }

    public String printAll(String tab, XMLComposite c) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tab).append("<").append(c.getTag()).append(" ")
                .append(XMLComposite.printAttributes(c.getAttributes())).append(">\n");

        for(XMLComponent component : c.getComponents()) {
            if(component instanceof XMLLeaf)
                stringBuilder.append(tab).append("\t").append(component).append("\n");
            else
                stringBuilder.append(printAll(tab + "\t", (XMLComposite) component)).append("\n");
        }

        stringBuilder.append(tab).append("</").append(c.getTag()).append(">");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return printAll("", this);
    }
}

public class XMLTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        XMLComponent component = new XMLLeaf("student", "Trajce Trajkovski");
        component.addAttribute("type", "redoven");
        component.addAttribute("program", "KNI");

        XMLComposite composite = new XMLComposite("name");
        composite.addComponent(new XMLLeaf("first-name", "trajce"));
        composite.addComponent(new XMLLeaf("last-name", "trajkovski"));
        composite.addAttribute("type", "redoven");
        component.addAttribute("program", "KNI");

        if (testCase==1) {
            //TODO Print the component object
            System.out.println(component.toString());
        } else if(testCase==2) {
            //TODO print the composite object
            System.out.println(composite.toString());
        } else if (testCase==3) {
            XMLComposite main = new XMLComposite("level1");
            main.addAttribute("level","1");
            XMLComposite lvl2 = new XMLComposite("level2");
            lvl2.addAttribute("level","2");
            XMLComposite lvl3 = new XMLComposite("level3");
            lvl3.addAttribute("level","3");
            lvl3.addComponent(component);
            lvl2.addComponent(lvl3);
            lvl2.addComponent(composite);
            lvl2.addComponent(new XMLLeaf("something", "blabla"));
            main.addComponent(lvl2);
            main.addComponent(new XMLLeaf("course", "napredno programiranje"));

            //TODO print the main object
            System.out.println(main.toString());
        }
    }
}
