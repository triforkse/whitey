package se.trifork.intellij.whitey;

public class RemoveWhitespaceAction extends WhitespaceReplacerAction {

    @Override
    protected String getReplacement() {
        return "";
    }
}
