/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homefinder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author alicia
 */
public class RangeSliderUI extends BasicSliderUI {

    private Rectangle thumbRectMax = null;
    
    public RangeSliderUI(RangeSlider rs) {
        super(rs);
    }

    @Override
    public void installUI(JComponent c)   {
        super.installUI(c); // This figures out where the labels, ticks, track, and thumb are.
        thumbRectMax = new Rectangle();
    }
    
    @Override
    public void uninstallUI(JComponent c)   {
        super.uninstallUI(c); // This figures out where the labels, ticks, track, and thumb are.
        thumbRectMax = null;
    }
    
    @Override
    protected void calculateThumbSize() {
        Dimension size = getThumbSize();
        thumbRect.setSize( size.width, size.height );
        thumbRectMax.setSize( size.width, size.height );
    }
    
    protected void calculateThumbMaxLocation() {
        if ( slider.getSnapToTicks() ) {
            int sliderValue = slider.getValueMax();
            int snappedValue = sliderValue;
            int tickSpacing = getTickSpacing();

            if ( tickSpacing != 0 ) {
                // If it's not on a tick, change the value
                if ( (sliderValue - slider.getMinimum()) % tickSpacing != 0 ) {
                    float temp = (float)(sliderValue - slider.getMinimum()) / (float)tickSpacing;
                    int whichTick = Math.round( temp );

                    // This is the fix for the bug #6401380
                    if (temp - (int)temp == .5 && sliderValue < lastValue) {
                      whichTick --;
                    }
                    snappedValue = slider.getMinimum() + (whichTick * tickSpacing);
                }

                if( snappedValue != sliderValue ) {
                    slider.setValue( snappedValue );
                }
            }
        }

        if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            int valuePosition = xPositionForValue(slider.getValue());

            thumbRect.x = valuePosition - (thumbRect.width / 2);
            thumbRect.y = trackRect.y;
        }
        else {
            int valuePosition = yPositionForValue(slider.getValue());

            thumbRect.x = trackRect.x;
            thumbRect.y = valuePosition - (thumbRect.height / 2);
        }
    }
    
    @Override
    public void paint(Graphics g, JComponent jc) {
        recalculateIfInsetsChanged();
        recalculateIfOrientationChanged();
        Rectangle clip = g.getClipBounds();

        if ( !clip.intersects(trackRect) && slider.getPaintTrack())
            calculateGeometry();

        if ( slider.getPaintTrack() && clip.intersects( trackRect ) ) {
            paintTrack( g );
        }
        if ( slider.getPaintTicks() && clip.intersects( tickRect ) ) {
            paintTicks( g );
        }
        if ( slider.getPaintLabels() && clip.intersects( labelRect ) ) {
            paintLabels( g );
        }
        if ( slider.hasFocus() && clip.intersects( focusRect ) ) {
            paintFocus( g );
        }
        if ( clip.intersects( thumbRect ) ) {
            paintThumb( g );
        }
        
    }
    
    public void paintThumbMax(Graphics g) {
        Rectangle knobBounds = thumbRectMax;
        int w = knobBounds.width;
        int h = knobBounds.height;

        g.translate(knobBounds.x, knobBounds.y);

        if ( slider.isEnabled() ) {
            g.setColor(slider.getBackground());
        }
        else {
            g.setColor(slider.getBackground().darker());
        }

        Boolean paintThumbArrowShape =
            (Boolean)slider.getClientProperty("Slider.paintThumbArrowShape");

        if ((!slider.getPaintTicks() && paintThumbArrowShape == null) ||
            paintThumbArrowShape == Boolean.FALSE) {

            // "plain" version
            g.fillRect(0, 0, w, h);

            g.setColor(Color.black);
            g.drawLine(0, h-1, w-1, h-1);
            g.drawLine(w-1, 0, w-1, h-1);

            g.setColor(highlightColor);
            g.drawLine(0, 0, 0, h-2);
            g.drawLine(1, 0, w-2, 0);

            g.setColor(shadowColor);
            g.drawLine(1, h-2, w-2, h-2);
            g.drawLine(w-2, 1, w-2, h-3);
        }
        else if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            int cw = w / 2;
            g.fillRect(1, 1, w-3, h-1-cw);
            Polygon p = new Polygon();
            p.addPoint(1, h-cw);
            p.addPoint(cw-1, h-1);
            p.addPoint(w-2, h-1-cw);
            g.fillPolygon(p);

            g.setColor(highlightColor);
            g.drawLine(0, 0, w-2, 0);
            g.drawLine(0, 1, 0, h-1-cw);
            g.drawLine(0, h-cw, cw-1, h-1);

            g.setColor(Color.black);
            g.drawLine(w-1, 0, w-1, h-2-cw);
            g.drawLine(w-1, h-1-cw, w-1-cw, h-1);

            g.setColor(shadowColor);
            g.drawLine(w-2, 1, w-2, h-2-cw);
            g.drawLine(w-2, h-1-cw, w-1-cw, h-2);
        }
        else {  // vertical
            int cw = h / 2;
            if(BasicGraphicsUtils.isLeftToRight(slider)) {
                  g.fillRect(1, 1, w-1-cw, h-3);
                  Polygon p = new Polygon();
                  p.addPoint(w-cw-1, 0);
                  p.addPoint(w-1, cw);
                  p.addPoint(w-1-cw, h-2);
                  g.fillPolygon(p);

                  g.setColor(highlightColor);
                  g.drawLine(0, 0, 0, h - 2);                  // left
                  g.drawLine(1, 0, w-1-cw, 0);                 // top
                  g.drawLine(w-cw-1, 0, w-1, cw);              // top slant

                  g.setColor(Color.black);
                  g.drawLine(0, h-1, w-2-cw, h-1);             // bottom
                  g.drawLine(w-1-cw, h-1, w-1, h-1-cw);        // bottom slant

                  g.setColor(shadowColor);
                  g.drawLine(1, h-2, w-2-cw,  h-2 );         // bottom
                  g.drawLine(w-1-cw, h-2, w-2, h-cw-1 );     // bottom slant
            }
            else {
                  g.fillRect(5, 1, w-1-cw, h-3);
                  Polygon p = new Polygon();
                  p.addPoint(cw, 0);
                  p.addPoint(0, cw);
                  p.addPoint(cw, h-2);
                  g.fillPolygon(p);

                  g.setColor(highlightColor);
                  g.drawLine(cw-1, 0, w-2, 0);             // top
                  g.drawLine(0, cw, cw, 0);                // top slant

                  g.setColor(Color.black);
                  g.drawLine(0, h-1-cw, cw, h-1 );         // bottom slant
                  g.drawLine(cw, h-1, w-1, h-1);           // bottom

                  g.setColor(shadowColor);
                  g.drawLine(cw, h-2, w-2,  h-2 );         // bottom
                  g.drawLine(w-1, 1, w-1,  h-2 );          // right
            }
        }

        g.translate(-knobBounds.x, -knobBounds.y);
    }
    
  
}
