package se.trifork.intellij.whitey;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

public abstract class WhitespaceReplacerAction extends AnAction {

    protected abstract String getReplacement();

    public int[] generateChart() {
        return new int[] {1, 2, 3};
    }


    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getProject();
        if (project == null) {
            return;
        }

        final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) {
            return;
        }

        String content = "Lorem ipsum dolor...";

        CaretAction removeWhitespaceExceptOne = new CaretAction() {

            @Override
            public void perform(Caret caret) {
                int caretOffset = caret.getOffset();

                String text = editor.getDocument().getText();

                // Find the first adjacent non-space character before the caret.

                int startOffset = 0;

                for (int i = caretOffset; i >= 0; i--) {
                    char c = text.charAt(i);
                    if (!Character.isWhitespace(c)) {
                        startOffset = i;
                        break;
                    }
                }

                // Find the first adjacent non-space character after the caret.

                int endOffset = 0;

                for (int i = caretOffset; i <= text.length(); i++) {
                    char c = text.charAt(i);
                    if (!Character.isWhitespace(c)) {
                        endOffset = i;
                        break;
                    }
                }

                if (startOffset == endOffset || startOffset == endOffset - 1) {
                    // There is no whitespace, just return.
                    return;
                } else {
                    // Plus one since we don't want to replace the first character
                    // but the whitespace after it.
                    startOffset += 1;
                }

                final int finalStartOffset = startOffset;
                final int finalEndOffset = endOffset;

                final CommandProcessor commandProcessor = CommandProcessor.getInstance();

                commandProcessor.executeCommand(project, new Runnable() {

                    @Override
                    public void run() {

                        ApplicationManager.getApplication().runWriteAction(new Runnable() {

                            @Override
                            public void run() {
                                editor.getDocument().replaceString(finalStartOffset, finalEndOffset, getReplacement());
                            }
                        });
                    }
                }, "replaceString()", commandProcessor.getCurrentCommandGroupId());
            }
        };

        editor.getCaretModel().runForEachCaret(removeWhitespaceExceptOne);
    }
}
