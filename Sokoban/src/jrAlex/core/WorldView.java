package jrAlex.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class WorldView extends View
{
	private static final long	serialVersionUID	= 1L;

	protected int				scale, i, widthInBlocks, heightInBlocks;
	protected World				world;
	protected boolean			drawMinimap;

	public WorldView(int scale)
	{
		setSize(MainWindow.getInstance().getWidth(), MainWindow.getInstance().getHeight());
		this.i = -1;
		this.scale = scale;
		this.widthInBlocks = getWidth() / scale;
		this.heightInBlocks = getHeight() / scale;
		this.drawMinimap = false;
		loadNext();
		setBackground(Color.white);
		setFocusable(true);
		grabFocus();
		addKeybind("W", new PlayerMoveAction(Direction.NORTH));
		addKeybind("S", new PlayerMoveAction(Direction.SOUTH));
		addKeybind("D", new PlayerMoveAction(Direction.EAST));
		addKeybind("A", new PlayerMoveAction(Direction.WEST));
		addKeybind("UP", new PlayerMoveAction(Direction.NORTH));
		addKeybind("DOWN", new PlayerMoveAction(Direction.SOUTH));
		addKeybind("RIGHT", new PlayerMoveAction(Direction.EAST));
		addKeybind("LEFT", new PlayerMoveAction(Direction.WEST));
		addKeybind("R", new AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				reloadWorld();
			}
		});
		addKeybind("Q", new AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				drawMinimap = !drawMinimap;
			}
		});
	}

	public WorldView(int scale, World world)
	{
		this(scale);
		this.world = world;
	}

	@Override
	public void update(int delta)
	{
		if (world.checkWin())
			loadNext();
	}

	public void loadNext()
	{
		world = World.load("test" + ++i);
	}

	public void reloadWorld()
	{
		world = World.load("test" + i);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Point playerCoords = world.getPlayerCoords();
		Graphics2D g2d = (Graphics2D) g;

		// Displace origin by player pos and center it
		g2d.translate((widthInBlocks / 2 - playerCoords.x) * scale, (heightInBlocks / 2 - playerCoords.y) * scale);

		// Draw objects
		for (int row = 0; row < world.getHeight(); row++)
			for (int col = 0; col < world.getWidth(); col++)
			{
				g2d.drawImage(Images.getImage("floor"), col * scale, row * scale, scale, scale, null);
				if (world.getObjects()[row][col] != null)
					world.getObjects()[row][col].render(g2d, col, row, scale);
			}

		// Draw endpoints
		for (Point point : world.endpoints)
			g.drawImage(Images.getImage("endpoint"), point.x * scale, point.y * scale, scale, scale, null);

		// Reset origin
		g2d.translate(-(widthInBlocks / 2 - playerCoords.x) * scale, -(heightInBlocks / 2 - playerCoords.y) * scale);

		if (drawMinimap)
		{
			int scale = Math.min(getWidth(), getHeight()) / Math.max(world.getWidth(), world.getHeight());
			g2d.scale(.5, .5);
			g2d.fill(getBounds());
			for (int row = 0; row < world.getHeight(); row++)
				for (int col = 0; col < world.getWidth(); col++)
				{
					g2d.drawImage(Images.getImage("floor"), col * scale, row * scale, scale, scale, null);
					if (world.getObjects()[row][col] != null)
						world.getObjects()[row][col].render(g2d, col, row, scale);
				}
			for (Point point : world.endpoints)
				g.drawImage(Images.getImage("endpoint"), point.x * scale, point.y * scale, scale, scale, null);
			g2d.scale(2, 2);
		}
	}

	public void setWorld(World world)
	{
		this.world = world;
	}

	class PlayerMoveAction extends AbstractAction
	{
		private static final long	serialVersionUID	= 1L;

		private Direction			dir;

		public PlayerMoveAction(Direction dir)
		{
			this.dir = dir;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			world.getPlayer().changeDir(dir);
			world.movePlayer();
		}
	}
}
