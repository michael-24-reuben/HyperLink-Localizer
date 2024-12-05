package fx.pisces.hyperlocal.utils.object;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ObservableSharedDataWrapper<T> {
    private final ObjectProperty<T> value = new SimpleObjectProperty<>();

    public ObservableSharedDataWrapper(T initialValue) {
        value.set(initialValue);
    }

    public ObservableSharedDataWrapper() {}

    public void set(T value) {
        this.value.set(value);
    }

    public T get() {
        return value.get();
    }

    public void setValue(T value) {
        this.value.set(value);
    }

    public ObjectProperty<T> valueProperty() {
        return value;
    }
}
