/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homefinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicSliderUI;


/**
 *
 * @author Alicia A.
 */
public class RangeSliderUI extends BasicSliderUI {

	// Rectangle used for the upper thumb
	private Rectangle thumbRectMax = null;

	// Redefining private variables used in the BasiSliderUI that we need
	private int lastValue;
	private boolean isDraggingMin;
	private boolean isDraggingMax;

	public RangeSliderUI(RangeSlider rs) {
		super(rs);
	}

	@Override
	public void installUI(JComponent c)   {
		thumbRectMax = new Rectangle();
		super.installUI(c); // This figures out where the labels, ticks, track, and thumb are.
	}

	@Override
	public void uninstallUI(JComponent c)   {
		thumbRectMax = null;
		super.uninstallUI(c); // This figures out where the labels, ticks, track, and thumb are.
	}

	@Override
	protected void calculateThumbSize() {
		super.calculateThumbSize();
		thumbRectMax.setSize(thumbRect.width, thumbRect.height);
	}
	
	/**
	 * Redefined to calculate the location of the upper Thumb also
	 */
	protected void calculateThumbLocation() {
		super.calculateThumbLocation();

		if ( slider.getSnapToTicks() ) {
			int sliderValue = slider.getValue() + slider.getExtent();
			int snappedValue = sliderValue;
			int tickSpacing = slider.getMajorTickSpacing();

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
			int valuePosition = xPositionForValue(slider.getValue() + slider.getExtent());

			thumbRectMax.x = valuePosition - (thumbRectMax.width / 2);
			thumbRectMax.y = trackRect.y;
		}
		else {
			int valuePosition = yPositionForValue(slider.getValue() + slider.getExtent());

			thumbRectMax.x = trackRect.x;
			thumbRectMax.y = valuePosition - (thumbRectMax.height / 2);
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
		/*
		 * Added to repaint the thumb of the upper value if necessary
		 */
		if ( clip.intersects( thumbRectMax ) ) {
			paintThumbMax( g );
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

			g.setColor(Color.blue);
			g.drawLine(0, 0, 0, h-2);
			g.drawLine(1, 0, w-2, 0);

			g.setColor(getShadowColor());
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

			g.setColor(getHighlightColor());
			g.drawLine(0, 0, w-2, 0);
			g.drawLine(0, 1, 0, h-1-cw);
			g.drawLine(0, h-cw, cw-1, h-1);

			g.setColor(Color.black);
			g.drawLine(w-1, 0, w-1, h-2-cw);
			g.drawLine(w-1, h-1-cw, w-1-cw, h-1);

			g.setColor(getShadowColor());
			g.drawLine(w-2, 1, w-2, h-2-cw);
			g.drawLine(w-2, h-1-cw, w-1-cw, h-2);
		}
		/* Commented because it was not working with BasicGraphicsUtils
		else {  // vertical
            int cw = h / 2;
            if(BasicGraphicsUtils.isLeftToRight(slider)) {
                  g.fillRect(1, 1, w-1-cw, h-3);
                  Polygon p = new Polygon();
                  p.addPoint(w-cw-1, 0);
                  p.addPoint(w-1, cw);
                  p.addPoint(w-1-cw, h-2);
                  g.fillPolygon(p);

                  g.setColor(getHighlightColor());
                  g.drawLine(0, 0, 0, h - 2);                  // left
                  g.drawLine(1, 0, w-1-cw, 0);                 // top
                  g.drawLine(w-cw-1, 0, w-1, cw);              // top slant

                  g.setColor(Color.black);
                  g.drawLine(0, h-1, w-2-cw, h-1);             // bottom
                  g.drawLine(w-1-cw, h-1, w-1, h-1-cw);        // bottom slant

                  g.setColor(getShadowColor());
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

                  g.setColor(getHighlightColor());
                  g.drawLine(cw-1, 0, w-2, 0);             // top
                  g.drawLine(0, cw, cw, 0);                // top slant

                  g.setColor(Color.black);
                  g.drawLine(0, h-1-cw, cw, h-1 );         // bottom slant
                  g.drawLine(cw, h-1, w-1, h-1);           // bottom

                  g.setColor(getShadowColor());
                  g.drawLine(cw, h-2, w-2,  h-2 );         // bottom
                  g.drawLine(w-1, 1, w-1,  h-2 );          // right
            }
        }*/

		g.translate(-knobBounds.x, -knobBounds.y);
	}

	/**
	 * Redefined to instanciate the listener
	 */
	protected TrackListener createTrackListener(JSlider slider) {
		return new RangeTrackListener();
	}
	
	public void setThumbMaxLocation(int x, int y)  {
		Rectangle unionRect = new Rectangle();
		unionRect.setBounds( thumbRectMax);
		thumbRectMax.setLocation( x, y );

		SwingUtilities.computeUnion( thumbRectMax.x, thumbRectMax.y, thumbRectMax.width, thumbRectMax.height, unionRect );
		slider.repaint( unionRect.x, unionRect.y, unionRect.width, unionRect.height );
	}

	/////////////////////////////////////////////////////////////////////////
	/// Track Listener Class
	/////////////////////////////////////////////////////////////////////////
	/**
	 * Track mouse movements.
	 *
	 * This class should be treated as a &quot;protected&quot; inner class.
	 * Instantiate it only within subclasses of <code>Foo</code>.
	 */
	public class RangeTrackListener extends BasicSliderUI.TrackListener {
		protected transient int offset;
		protected transient int currentMouseX, currentMouseY;

		public void mouseReleased(MouseEvent e) {
			if (!slider.isEnabled()) {
				return;
			}

			offset = 0;
			scrollTimer.stop();

			isDraggingMin = false;
			isDraggingMax = false;
			slider.setValueIsAdjusting(false);
			slider.repaint();
		}

		/**
		 * If the mouse is pressed above the "thumb" component
		 * then reduce the scrollbars value by one page ("page up"),
		 * otherwise increase it by one page.  If there is no
		 * thumb then page up if the mouse is in the upper half
		 * of the track.
		 */
		public void mousePressed(MouseEvent e) {
			if (!slider.isEnabled()) {
				return;
			}

			// We should recalculate geometry just before
			// calculation of the thumb movement direction.
			// It is important for the case, when JSlider
			// is a cell editor in JTable. See 6348946.
			calculateGeometry();

			currentMouseX = e.getX();
			currentMouseY = e.getY();

			if (slider.isRequestFocusEnabled()) {
				slider.requestFocus();
			}

			// Clicked in the lower Thumb area?
			if (thumbRect.contains(currentMouseX, currentMouseY)) {
				if (UIManager.getBoolean("Slider.onlyLeftMouseButtonDrag")
						&& !SwingUtilities.isLeftMouseButton(e)) {
					return;
				}

				switch (slider.getOrientation()) {
				case JSlider.VERTICAL:
					offset = currentMouseY - thumbRect.y;
					break;
				case JSlider.HORIZONTAL:
					offset = currentMouseX - thumbRect.x;
					break;
				}
				isDraggingMin = true;
				return;
			}
			// Clicked in the upper Thumb area?
			if (thumbRectMax.contains(currentMouseX, currentMouseY)) {
				if (UIManager.getBoolean("Slider.onlyLeftMouseButtonDrag")
						&& !SwingUtilities.isLeftMouseButton(e)) {
					return;
				}

				switch (slider.getOrientation()) {
				case JSlider.VERTICAL:
					offset = currentMouseY - thumbRectMax.y;
					break;
				case JSlider.HORIZONTAL:
					offset = currentMouseX - thumbRectMax.x;
					break;
				}
				isDraggingMax = true;
				return;
			}

			if (!SwingUtilities.isLeftMouseButton(e)) {
				return;
			}

			isDraggingMin = false;
			isDraggingMax = false;
			slider.setValueIsAdjusting(true);
			
		}

		/**
		 * Set the models value to the position of the top/left
		 * of the thumb relative to the origin of the track.
		 */
		public void mouseDragged(MouseEvent e) {
			int thumbMiddle;

			if (!slider.isEnabled()) {
				return;
			}

			currentMouseX = e.getX();
			currentMouseY = e.getY();

			if (!isDraggingMin && !isDraggingMax) {
				return;
			}

			slider.setValueIsAdjusting(true);

			if(isDraggingMin) {
				switch (slider.getOrientation()) {
				case JSlider.VERTICAL:
					int halfThumbHeight = thumbRect.height / 2;
					int thumbTop = e.getY() - offset;
					int trackTop = trackRect.y;
					int trackBottom = trackRect.y + (trackRect.height - 1);
					int vMax = yPositionForValue(slider.getValue() +
							slider.getExtent());

					if (drawInverted()) {
						trackBottom = vMax;
					}
					else {
						trackTop = vMax;
					}
					thumbTop = Math.max(thumbTop, trackTop - halfThumbHeight);
					thumbTop = Math.min(thumbTop, trackBottom - halfThumbHeight);

					setThumbLocation(thumbRect.x, thumbTop);

					thumbMiddle = thumbTop + halfThumbHeight;
					slider.setValue( valueForYPosition( thumbMiddle ) );
					break;
				case JSlider.HORIZONTAL:
					int halfThumbWidth = thumbRect.width / 2;
					int thumbLeft = e.getX() - offset;
					int trackLeft = trackRect.x;
					int trackRight = trackRect.x + (trackRect.width - 1);
					int hMax = xPositionForValue(slider.getValue() +
							slider.getExtent());

					if (drawInverted()) {
						trackLeft = hMax;
					}
					else {
						trackRight = hMax;
					}
					thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
					thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

					setThumbLocation(thumbLeft, thumbRect.y);

					thumbMiddle = thumbLeft + halfThumbWidth;
					slider.setValue(valueForXPosition(thumbMiddle));
					break;
				}
			} 
			else if(isDraggingMax) {
				switch (slider.getOrientation()) {
				case JSlider.VERTICAL:
					int halfThumbHeight = thumbRectMax.height / 2;
					int thumbTop = e.getY() - offset;
					int trackTop = trackRect.y;
					int trackBottom = trackRect.y + (trackRect.height - 1);
					int vMin = yPositionForValue(slider.getValue());

					if (drawInverted()) {
						trackBottom = vMin;
					}
					else {
						trackTop = vMin;
					}
					thumbTop = Math.max(thumbTop, trackTop - halfThumbHeight);
					thumbTop = Math.min(thumbTop, trackBottom - halfThumbHeight);

					setThumbMaxLocation(thumbRectMax.x, thumbTop);

					thumbMiddle = thumbTop + halfThumbHeight;
					slider.setExtent( valueForYPosition( thumbMiddle ) - slider.getValue());
					break;
				case JSlider.HORIZONTAL:
					int halfThumbWidth = thumbRectMax.width / 2;
					int thumbLeft = e.getX() - offset;
					int trackLeft = trackRect.x;
					int trackRight = trackRect.x + (trackRect.width - 1);
					int hMin = xPositionForValue(slider.getValue());

					if (drawInverted()) {
						trackRight = hMin;
					}
					else {
						trackLeft = hMin;
					}
					thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
					thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

					setThumbMaxLocation(thumbLeft, thumbRectMax.y);

					thumbMiddle = thumbLeft + halfThumbWidth;
					slider.setExtent(valueForXPosition(thumbMiddle) - slider.getValue());
					break;
				}
			}
		}

	}

}
