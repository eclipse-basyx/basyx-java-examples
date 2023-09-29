package nav_msgs;

import geometry_msgs.Point;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class GridCells extends RosMessage{
	private Header header;
	private Float cell_width;
	private Float cell_height;
	private Point[] cells;
	
	public GridCells() {
		
	}
    
	public GridCells(Header header, Float cell_width, Float cell_height, Point[] cells) {
		this.setHeader(header);
		this.setCell_width(cell_width);
		this.setCell_height(cell_height);
		this.setCells(cells);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Float getCell_width() {
		return cell_width;
	}

	public void setCell_width(Float cell_width) {
		this.cell_width = cell_width;
	}

	public Float getCell_height() {
		return cell_height;
	}

	public void setCell_height(Float cell_height) {
		this.cell_height = cell_height;
	}

	public Point[] getCells() {
		return cells;
	}

	public void setCells(Point[] cells) {
		this.cells = cells;
	}


}
