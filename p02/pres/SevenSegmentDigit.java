package p02.pres;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SevenSegmentDigit extends JPanel {
    private int value = 0;

    private final List<StartEventListener> startListeners = new ArrayList<>();
    private final List<PlusOneEventListener> plusOneListeners = new ArrayList<>();
    private final List<ResetEventListener> resetListeners = new ArrayList<>();

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        repaint();
    }

    public void addStartEventListener(StartEventListener listener) {
        startListeners.add(listener);
    }

    public void addPlusOneEventListener(PlusOneEventListener listener) {
        plusOneListeners.add(listener);
    }

    public void addResetEventListener(ResetEventListener listener) {
        resetListeners.add(listener);
    }

    public void triggerStartEvent() {
        setValue(0);
        for (StartEventListener listener : startListeners) {
            listener.onStartEvent();
        }
        repaint();
    }

    public void triggerPlusOneEvent() {
        value = (value + 1) % 10;
        for (PlusOneEventListener listener : plusOneListeners) {
            listener.onPlusOneEvent();
        }
        repaint();
    }

    public void triggerResetEvent() {
        setValue(0);
        for (ResetEventListener listener : resetListeners) {
            listener.onResetEvent();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g2d = (Graphics2D) gr;

        g2d.setColor(new Color(255, 24, 24));

        int w = getWidth();
        int h = getHeight();
        int segLength = Math.min(w, h) / 5;
        int margin = 11;

        Rectangle a = new Rectangle(margin, margin, w - 2 * margin, segLength);
        Rectangle b = new Rectangle(w - margin - segLength, margin, segLength, h / 2 - margin);
        Rectangle c = new Rectangle(w - margin - segLength, h / 2, segLength, h / 2 - margin);
        Rectangle d = new Rectangle(margin, h - margin - segLength, w - 2 * margin, segLength);
        Rectangle e = new Rectangle(margin, h / 2, segLength, h / 2 - margin);
        Rectangle f = new Rectangle(margin, margin, segLength, h / 2 - margin);
        Rectangle g = new Rectangle(margin, h / 2 - segLength / 2, w - 2 * margin, segLength);

        switch (value) {
            case 0 -> draw(g2d, a, b, c, d, e, f);
            case 1 -> draw(g2d, b, c);
            case 2 -> draw(g2d, a, b, g, e, d);
            case 3 -> draw(g2d, a, b, g, c, d);
            case 4 -> draw(g2d, f, g, b, c);
            case 5 -> draw(g2d, a, f, g, c, d);
            case 6 -> draw(g2d, a, f, e, d, c, g);
            case 7 -> draw(g2d, a, b, c);
            case 8 -> draw(g2d, a, b, c, d, e, f, g);
            case 9 -> draw(g2d, a, b, c, d, f, g);
        }
    }

    private void draw(Graphics2D g2d, Rectangle... segments) {
        for (Rectangle r : segments) {
            g2d.fill(r);
        }
    }
}