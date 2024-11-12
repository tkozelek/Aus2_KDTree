package kozelek.GUI.contoller;

import kozelek.GUI.Wrapper;
import kozelek.GUI.views.EditView;
import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.GPSObjekt;
import kozelek.data.objekt.interfaces.IObject;

public class EditController<T extends IObject> {
    private EditView<T> view;
    private Wrapper wrapper;
    private MainController<T> mainController;

    public EditController(EditView<T> view, Wrapper wrapper, MainController<T> mainController) {
        this.view = view;
        this.wrapper = wrapper;
        this.mainController = mainController;

        this.view.getSubmitEditButton().addActionListener(e -> submitedit());
        this.view.getBackButton().addActionListener(e -> goBack());
    }

    private void goBack() {
        this.view.dispose();
    }

    private void submitedit() {
        int scislo = Integer.parseInt(this.view.getCisloField().getText());
        String spopis = this.view.getDescField().getText();
        double x1 = Double.parseDouble(this.view.getX1Field().getText());
        double y1 = Double.parseDouble(this.view.getY1Field().getText());
        double x2 = Double.parseDouble(this.view.getX2Field().getText());
        double y2 = Double.parseDouble(this.view.getY2Field().getText());

        T item = null;
        T item2 = null;
        if (this.view.getData() instanceof Nehnutelnost) { // je nehnutelnost
            item = (T) new Nehnutelnost(scislo, spopis, new GPS(x1, y1));
            item2 = (T) new Nehnutelnost(scislo, spopis, new GPS(x2, y2));

            this.wrapper.upravNehnutelnost((Nehnutelnost) this.view.getData(), (Nehnutelnost) item);
            this.wrapper.upravNehnutelnost((Nehnutelnost) this.view.getOther(), (Nehnutelnost) item2);

        } else if (this.view.getData() instanceof Parcela) {
            item = (T) new Parcela(scislo, spopis, new GPS(x1, y1));
            item2 = (T) new Parcela(scislo, spopis, new GPS(x2, y2));

            this.wrapper.upravParcelu((Parcela) this.view.getData(), (Parcela) item);
            this.wrapper.upravParcelu((Parcela) this.view.getOther(), (Parcela) item2);
        }

        this.mainController.replaceInModel(this.view.getData(), item);
        if (this.view.getOther() != null) {
            this.mainController.replaceInModel(this.view.getOther(), item2);
        }
        this.view.setData(item);

    }


}
