package br.net.eia.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import br.net.eia.ui.dialog.DialogUtils;

public class ProgressDialog extends Dialog<Void> {
    

    public ProgressDialog(final Worker<?> worker) {
        if (worker != null
                && (worker.getState() == State.CANCELLED
                || worker.getState() == State.FAILED
                || worker.getState() == State.SUCCEEDED)) {
            return;
        }
        setResultConverter(dialogButton -> null);
                
        final DialogPane dialogPane = getDialogPane();
                
       // setTitle(getString("progress.dlg.title")); //$NON-NLS-1$
        //dialogPane.setHeaderText(getString("progress.dlg.header")); //$NON-NLS-1$
        
        final Label progressMessage = new Label();
        progressMessage.textProperty().bind(worker.messageProperty());

        final WorkerProgressPane content = new WorkerProgressPane(this);
        content.setMaxWidth(Double.MAX_VALUE);
        content.setWorker(worker);
        
        VBox vbox = new VBox(10, progressMessage, content);
        vbox.setMaxWidth(Double.MAX_VALUE);
        vbox.setPrefSize(700, 200);
        /**
         * The content Text cannot be set before the constructor and since we
         * set a Content Node, the contentText will not be shown. If we want to
         * let the user display a content text, we must recreate it.
         */
        Label contentText = new Label();
        contentText.setWrapText(true);
        vbox.getChildren().add(0, contentText);
        contentText.textProperty().bind(dialogPane.contentTextProperty());
        dialogPane.setContent(vbox);
    }
    

    
    /**************************************************************************
     * 
     * Support classes
     * 
     **************************************************************************/

    /**
     * The WorkerProgressPane takes a {@link Dialog} and a {@link Worker}
     * and links them together so the dialog is shown or hidden depending
     * on the state of the worker.  The WorkerProgressPane also includes
     * a progress bar that is automatically bound to the progress property
     * of the worker.  The way in which the WorkerProgressPane shows and
     * hides its worker's dialog is consistent with the expected behavior
     * for {@link #showWorkerProgress(Worker)}.
     */
    private static class WorkerProgressPane extends Region {
        private Worker<?> worker;

        private boolean dialogVisible = false;
        private boolean cancelDialogShow = false;

        private ChangeListener<Worker.State> stateListener = new ChangeListener<Worker.State>() {
            @Override public void changed(ObservableValue<? extends State> observable, State old, State value) {
                switch(value) {
                    case CANCELLED:
                    case FAILED:
                    case SUCCEEDED:
                        if(!dialogVisible) {
                            cancelDialogShow = true;
                            end();
                        } else if(old == State.SCHEDULED || old == State.RUNNING) {
                            end();
                        }
                        break;
                    case SCHEDULED:
                        begin();
                        break;
                    default: //no-op     
                }
            }
        };

        public final void setWorker(final Worker<?> newWorker) { 
            if (newWorker != worker) {
                if (worker != null) {
                    worker.stateProperty().removeListener(stateListener);
                    end();
                }

                worker = newWorker;

                if (newWorker != null) {
                    newWorker.stateProperty().addListener(stateListener);
                    if (newWorker.getState() == Worker.State.RUNNING || newWorker.getState() == Worker.State.SCHEDULED) {
                        // It is already running
                        begin();
                    }
                }
            }
        }

        // If the progress indicator changes, then we need to re-initialize
        // If the worker changes, we need to re-initialize

        private final ProgressDialog dialog;
        private final ProgressBar progressBar;
        
        public WorkerProgressPane(ProgressDialog dialog) {
            this.dialog = dialog;
            
            this.progressBar = new ProgressBar();
            progressBar.setMaxWidth(Double.MAX_VALUE);
            getChildren().add(progressBar);
            
            if (worker != null) {
                progressBar.progressProperty().bind(worker.progressProperty());
            }
        }

        private void begin() {
            // Platform.runLater needs to be used to show the dialog because
            // the call begin() is going to be occurring when the worker is
            // notifying state listeners about changes.  If Platform.runLater
            // is not used, the call to show() will cause the worker to get
            // blocked during notification and it will prevent the worker
            // from performing any additional notification for state changes.
            //
            // Sine the dialog is hidden as a result of a change in worker
            // state, calling show() without wrapping it in Platform.runLater
            // will cause the progress dialog to run forever when the dialog
            // is attached to workers that start out with a state of READY.
            //
            // This also creates a case where the worker's state can change
            // to finished before the dialog is shown, resulting in an
            // an attempt to hide the dialog before it is shown.  It's
            // necessary to track whether or not this occurs, so flags are
            // set to indicate if the dialog is visible and if if the call
            // to show should still be allowed.
            cancelDialogShow = false;

            Platform.runLater(() -> {
                if(!cancelDialogShow) {
                    progressBar.progressProperty().bind(worker.progressProperty());
                    dialogVisible = true;
                    dialog.show();
                }
            });
        }

        private void end() {
            progressBar.progressProperty().unbind();
            dialogVisible = false;
            DialogUtils.forcefullyHideDialog(dialog);
        }

        @Override protected void layoutChildren() {
            if (progressBar != null) {
                Insets insets = getInsets();
                double w = getWidth() - insets.getLeft() - insets.getRight();
                double h = getHeight() - insets.getTop() - insets.getBottom();

                double prefH = progressBar.prefHeight(-1);
                double x = insets.getLeft() + (w - w) / 2.0;
                double y = insets.getTop() + (h - prefH) / 2.0;

                progressBar.resizeRelocate(x, y, w, prefH);
            }
        }
    }
}
