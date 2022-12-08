package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import models.*;

/**
 *
 * @author Familia
 */
public class Pizarra extends JFrame implements Runnable {

	@Override
	public void run() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);
	}

	public Pizarra() {
		initComponents();
	}

	private void initComponents() {
            
		leftPanel = new JPanel();
		centerPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanelTitle = new JLabel();

		buttonGroup = new ButtonGroup();
		btnTriangulo = new JToggleButton();
		btnCuadrilatero = new JToggleButton();
		btnCirculo = new JToggleButton();
                btnPentagono = new JToggleButton();
                btnEstrella = new JToggleButton();

		canvasPanel = new CanvasPanel();
		logger = new JTextArea();

		rightPanelTitle = new JLabel();
		scrollFiguras = new JScrollPane();
		listFiguras = new JList<>(new ListFigurasModel());
                
		setTitle("Figuras Geometricas");
		setResizable(false);
		setLayout(new BorderLayout());

		KeyboardFocusManager.
						getCurrentKeyboardFocusManager().
						addPropertyChangeListener("focusOwner", (PropertyChangeEvent e) -> {
							
							requestFocusInWindow();
						});

		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {

				FiguraModel selectedModel = listFiguras.getSelectedValue();

				if (e.getExtendedKeyCode() == VK_SPACE && selectedModel != null) {
					logger.append(selectedModel.getNombre() + " deseleccionado.");
					listFiguras.clearSelection();
					return;
				}

				if (selectedModel == null || !selectedModel.canDraw()) {
					return;
				}

				switch (e.getExtendedKeyCode()) {
					case VK_W: {
						
						selectedModel.getPuntos().up(5);
                                                break;
					}
					case VK_S: {
                                            
						selectedModel.getPuntos().down(5);
                                                break;
					}
					case VK_A: {
                                            
						selectedModel.getPuntos().left(5);
                                                break;
					}
					case VK_D: {
                                            
						selectedModel.getPuntos().right(5);
                                                break;
					}
					case VK_Q: {
                                            
						selectedModel.getPuntos().rotateLeft(90);
                                                break;
					}
					case VK_E: {
                                            
						selectedModel.getPuntos().rotateRight(90);
                                                break;
					}
					case VK_Z: {
                                            
						selectedModel.getPuntos().zoomIn(2);
                                                break;
					}
					case VK_X: {
                                            
						selectedModel.getPuntos().zoomOut(2);
                                                break;
					}case VK_C: {
                                            
                                                JColorChooser vC = new JColorChooser();
                                                color = vC.showDialog(null, "Seleccione un Color", Color.BLACK);
                                                selectedModel.setBackground(color);
                                                break;
                                        }case VK_DELETE:{
                                                
                                                listModel.removeElement(selectedModel);
                                                selectedModel = null;
                                                break;
                                        }
				}

				repaint();
			}

		});

		/* leftPanel components & design */
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
                leftPanel.setBackground(Color.WHITE);

		btnTriangulo.setText("Triángulo    ");
		btnTriangulo.setFont(new Font("Montserrat", Font.PLAIN, 16));
                btnTriangulo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnTriangulo.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			btnTriangulo.setEnabled(false);
			btnCuadrilatero.setEnabled(true);
			btnCirculo.setEnabled(true);
                        btnPentagono.setEnabled(true);
                        btnEstrella.setEnabled(true);

			currentFigura = new TrianguloModel();
		});

		btnCuadrilatero.setText("Cuadrilatero");
		btnCuadrilatero.setFont(new Font("Montserrat", Font.PLAIN, 16));
                btnCuadrilatero.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCuadrilatero.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			btnCuadrilatero.setEnabled(false);
			btnTriangulo.setEnabled(true);
			btnCirculo.setEnabled(true);
                        btnPentagono.setEnabled(true);
                        btnEstrella.setEnabled(true);

			currentFigura = new CuadrilateroModel();
		});

		btnCirculo.setText("Circulo        ");
		btnCirculo.setFont(new Font("Montserrat", Font.PLAIN, 16));
                btnCirculo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCirculo.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			btnCirculo.setEnabled(false);
			btnCuadrilatero.setEnabled(true);
			btnTriangulo.setEnabled(true);
                        btnPentagono.setEnabled(true);
                        btnEstrella.setEnabled(true);

			currentFigura = new CirculoModel();
		});
                
                btnPentagono.setText("Pentagono   ");
                btnPentagono.setFont(new Font("Montserrat", Font.PLAIN, 16));
                btnPentagono.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnPentagono.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			btnPentagono.setEnabled(false);
			btnCuadrilatero.setEnabled(true);
			btnTriangulo.setEnabled(true);
                        btnCirculo.setEnabled(true);
                        btnEstrella.setEnabled(true);

			currentFigura = new PentagonoModel();
		});
                
                btnEstrella.setText("Estrella        ");
                btnEstrella.setFont(new Font("Montserrat", Font.PLAIN, 16));
                btnEstrella.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnEstrella.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

                        btnEstrella.setEnabled(false);
			btnPentagono.setEnabled(true);
			btnCuadrilatero.setEnabled(true);
			btnTriangulo.setEnabled(true);
                        btnCirculo.setEnabled(true);

			currentFigura = new EstrellaModel();
		});

		buttonGroup.add(btnTriangulo);
		buttonGroup.add(btnCuadrilatero);
		buttonGroup.add(btnCirculo);
                buttonGroup.add(btnPentagono);
                buttonGroup.add(btnEstrella);

		leftPanel.add(leftPanelTitle);
		leftPanel.add(btnTriangulo);
		leftPanel.add(btnCuadrilatero);
		leftPanel.add(btnCirculo);
                leftPanel.add(btnPentagono);
                leftPanel.add(btnEstrella);

		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);

		canvasPanel.setBackground(centerPanel.getBackground());
		canvasPanel.setBorder(new LineBorder(Color.BLACK, 1));
		canvasPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (currentFigura == null) {
					return;
				}

				for (int i = 0; i < currentFigura.getPuntos().size(); i++) {

					if (currentFigura.getPuntos().getValueAt(i) == null) {

						currentFigura.getPuntos().setValueAt(i, e.getPoint());

						listModel.addElement(currentFigura);
						canvasPanel.setModel(listModel);
						repaint();
						break;
					}
				}

				if (currentFigura.canDraw()) {
					try {
						currentFigura = (FiguraModel) currentFigura.getClass().getConstructors()[0].newInstance(new Object[0]);
					} catch (Exception ex) {

					}
				}

			}

		});

		centerPanel.add(canvasPanel, BorderLayout.CENTER);

		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
                rightPanel.setBackground(Color.WHITE);

		rightPanelTitle.setText("Figuras");
		rightPanelTitle.setFont(new Font("Montserrat", Font.BOLD, 24));

		listModel = (ListFigurasModel) listFiguras.getModel();
		listFiguras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		listFiguras.addListSelectionListener((ListSelectionEvent e) -> {

			listModel.unselectAll();

			var selectedModel = listFiguras.getSelectedValue();

			if (selectedModel == null) {
				repaint();
				return;
			}

			selectedModel.setSelected(true);
			repaint();
		});

		scrollFiguras.setViewportView(listFiguras);

		rightPanel.add(rightPanelTitle);
		rightPanel.add(scrollFiguras);

		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

	}

	private FiguraModel currentFigura;

	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JLabel leftPanelTitle;

	private ButtonGroup buttonGroup;
	private JToggleButton btnTriangulo;
	private JToggleButton btnCuadrilatero;
	private JToggleButton btnCirculo;
        private JToggleButton btnPentagono;
        private JToggleButton btnEstrella;
        
        private Color color;

	private CanvasPanel canvasPanel;
	private JTextArea logger;

	private JLabel rightPanelTitle;
	private JScrollPane scrollFiguras;
	private JList<FiguraModel> listFiguras;
	private ListFigurasModel listModel;

}
