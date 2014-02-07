package org.jcsoft.practicavlcbuena.guivlc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JSlider;
import javax.swing.JScrollBar;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;














import org.jcsoft.practicavlcbuena.filters.VideoFiltro;


import org.jcsoft.practicavlcbuena.util.Ficheros;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.mrl.BaseDvdMrl;
import uk.co.caprica.vlcj.mrl.SimpleDvdMrl;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.Cursor;
import java.awt.ComponentOrientation;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;

public class VentanaVlc {

	private JFrame frmMyvlc;
	private JPanel panel;
	private JSlider sliderReproductor;
	private JScrollBar scrollBarVolum;
	private JButton btPlay;
	private JButton btStop;
	private JButton btReview;
	private JButton btForward;
	private JPanel panel_1;
	private JMenuBar menuBar;
	private JInternalFrame internalFrame;
	private EmbeddedMediaPlayerComponent mediaPlayer;
	public enum Estado {STOP,PLAY,PAUSE};
	private Estado estado;
	private File ficheroVideo;
	private File ficheroSubtitulo;
	
	
	private static final String LIB_VLC = "D:\\DAM_2\\PMDM\\Versiones_descomprimidas\\vlc-2.1.2";
	private JMenu mnArchivo;
	private JMenuItem mntmAbrir;
	private JList miLista;
	private ArrayList <String> arrayRutas ;
	private JLabel lbTiempo;
	private JMenu mnVideo;
	private JMenuItem mntmPistaSubtiitulos;
	private JMenuItem mntmSalir;
	private JButton btnCrearlista;
	private JButton btnCargarlista;
	private JMenuItem mntmCapturarpantalla;
	private JMenu mnGeometriasize;
	private JMenuItem menuItem;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem menuItem_1;
	private JLabel lblVolumen;
	private JMenuItem mntmCargardvd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaVlc window = new VentanaVlc();
					window.frmMyvlc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaVlc() {
		
		inicializarVLCJ();
		initialize();
		iniciarVideo();
		arrayRutas = new ArrayList<String>();
	}

	private void iniciarVideo() {
		
		internalFrame.setContentPane(mediaPlayer);
		internalFrame.setVisible(true);
		
		estado = Estado.STOP;
		
	}
	public void stop() {
		
		mediaPlayer.getMediaPlayer().stop();
		this.estado = Estado.STOP;
		btPlay.setText(">");
		
		sliderReproductor.setValue(0);
	}
	
	
	
	private void abrirVideo() {
		
		JFileChooser jfc = new JFileChooser();
		jfc.addChoosableFileFilter(new VideoFiltro());
		jfc.setAcceptAllFileFilterUsed(false);
		if (jfc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
			return;
		
		ficheroVideo = jfc.getSelectedFile();
		arrayRutas.add(ficheroVideo.getAbsolutePath());
		estado = Estado.STOP;
		reproducirVideo();
	}
	private void avanceSlider() {
		
		mediaPlayer.getMediaPlayer().setTime((mediaPlayer.getMediaPlayer().getLength()*
				sliderReproductor.getValue())/100);
		
	}

	private void reproducirVideo() {
		
		// El reproductor está parado
				if (estado == Estado.STOP) {
					internalFrame.setTitle(ficheroVideo.getName());
					mediaPlayer.getMediaPlayer().playMedia(ficheroVideo.getAbsolutePath());
					estado = Estado.PLAY;	
					btPlay.setText("||");
				}
				// El reproductor está pausado
				else if (estado == Estado.PAUSE) {
					mediaPlayer.getMediaPlayer().play();
					estado = Estado.PLAY;
					btPlay.setText("||");
				}
				// El reproductor está reproduciendo
				else {
					mediaPlayer.getMediaPlayer().pause();
					
					estado = Estado.PAUSE;
					btPlay.setText(">");
				}
		
	}

	private void inicializarVLCJ() {
		
		cargarLibreria();
		mediaPlayer = new EmbeddedMediaPlayerComponent();
		
	}

	private void cargarLibreria() {
		
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), LIB_VLC);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);
		
	}
	private void paAtras() {
		mediaPlayer.getMediaPlayer().setTime(mediaPlayer.getMediaPlayer().getTime()-15000);
		
	}
	private void paAdelante() {
		mediaPlayer.getMediaPlayer().setTime(mediaPlayer.getMediaPlayer().getTime()+15000);
		
	}
	public void cargarDvd(){
		
		
		String mrl = new SimpleDvdMrl().device("/D:\\").title(0).chapter(0).angle(1).value();
		mediaPlayer.getMediaPlayer().playMedia(mrl);
	}
		

	public void subtitulos(){

		JFileChooser subti = new JFileChooser();
		subti.addChoosableFileFilter(new VideoFiltro());
		subti.setAcceptAllFileFilterUsed(false);
		if (subti.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
			return;
		
		ficheroSubtitulo = subti.getSelectedFile();
		
	    mediaPlayer.getMediaPlayer().setSubTitleFile(ficheroSubtitulo);

	}
	private void reproducirElemento() {
		
		//miLista.setListData(this.arrayRutas.toArray());
		System.out.println(arrayRutas.get(miLista.getSelectedIndex()));
		mediaPlayer.getMediaPlayer().playMedia(arrayRutas.get(miLista.getSelectedIndex()));
		
	}
	
	private void crearListas() {
		try {
			JFileChooser jc = new JFileChooser();
			if(jc.showOpenDialog(null)==JFileChooser.CANCEL_OPTION)
				return;
			
			jc.addChoosableFileFilter(new VideoFiltro());
			jc.setAcceptAllFileFilterUsed(false);
		
			ficheroVideo = jc.getSelectedFile();
			arrayRutas.add(ficheroVideo.getAbsolutePath());
			
			Ficheros.escribirObjeto(arrayRutas,"lista.txt" );
			JOptionPane.showMessageDialog(null, "Archivos añadidos a la lista");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void cargarLista(){
		
		try {
			Ficheros.leerObjeto("lista.txt");
			
			miLista.setListData(this.arrayRutas.toArray());
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void capturar() {
		mediaPlayer.getMediaPlayer().saveSnapshot();
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		
		frmMyvlc = new JFrame();
		frmMyvlc.setTitle("MyVLC");
		frmMyvlc.setBounds(100, 100, 586, 315);
		frmMyvlc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		frmMyvlc.getContentPane().add(panel, BorderLayout.SOUTH);
		
		sliderReproductor = new JSlider();
		sliderReproductor.setValue(0);
		sliderReproductor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				avanceSlider();
			}

			
		});
		
		btnCrearlista = new JButton("Anadir_a_Lista");
		btnCrearlista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				crearListas();
			}

			
		});
		panel.add(btnCrearlista);
		
		btnCargarlista = new JButton("cargarLista");
		btnCargarlista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarLista();
			}
		});
		panel.add(btnCargarlista);
		
		lbTiempo = new JLabel("");
		lbTiempo.setPreferredSize(new Dimension(35, 8));
		panel.add(lbTiempo);
		sliderReproductor.setPreferredSize(new Dimension(250, 23));
		panel.add(sliderReproductor);
		
		btPlay = new JButton("Play");
		btPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reproducirVideo();
			}
		});
		panel.add(btPlay);
		
		btStop = new JButton("Stop");
		btStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stop();
			}
		});
		
		scrollBarVolum = new JScrollBar();
		scrollBarVolum.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		scrollBarVolum.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		scrollBarVolum.setValue(50);
		scrollBarVolum.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				Ajustarvolumen();
			}

			private void Ajustarvolumen() {
				
				mediaPlayer.getMediaPlayer().setVolume(scrollBarVolum.getValue());
				
			}
		});
		
		lblVolumen = new JLabel("volumen");
		panel.add(lblVolumen);
		scrollBarVolum.setPreferredSize(new Dimension(15, 80));
		panel.add(scrollBarVolum);
		panel.add(btStop);
		
		btReview = new JButton("<<");
		btReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paAtras();
			}

		
		});
		panel.add(btReview);
		
		btForward = new JButton(">>");
		btForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paAdelante();
			}

			
		});
		panel.add(btForward);
		
		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(125, 10));
		frmMyvlc.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		miLista = new JList<>();
		miLista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				reproducirElemento();
			}
		});
		miLista.setModel(new AbstractListModel() {
			String[] values = new String[] {"Lista vacia"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		
		miLista.setPreferredSize(new Dimension(24, 0));
		miLista.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				reproducirElemento();
			}

			
		});
		panel_1.add(miLista, BorderLayout.CENTER);
		
		internalFrame = new JInternalFrame("");
		internalFrame.setPreferredSize(new Dimension(15, 45));
		internalFrame.setFrameIcon(null);
		frmMyvlc.getContentPane().add(internalFrame, BorderLayout.CENTER);
		internalFrame.setVisible(true);
		
		menuBar = new JMenuBar();
		frmMyvlc.setJMenuBar(menuBar);
		
		mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmAbrir = new JMenuItem("Abrir...");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirVideo();
			}
		});
		mnArchivo.add(mntmAbrir);
		
		mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMyvlc.dispose();
			}
		});
		mnArchivo.add(mntmSalir);
		
		mnVideo = new JMenu("Video");
		menuBar.add(mnVideo);
		
		mntmPistaSubtiitulos = new JMenuItem("Pista subtiitulos...");
		mntmPistaSubtiitulos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				subtitulos();
			}

			
		});
		mnVideo.add(mntmPistaSubtiitulos);
		
		mntmCapturarpantalla = new JMenuItem("CapturarPantalla");
		mntmCapturarpantalla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				capturar();
			}

			
		});
		mnVideo.add(mntmCapturarpantalla);
		
		mnGeometriasize = new JMenu("GeometriaSize");
		mnVideo.add(mnGeometriasize);
		
		menuItem = new JMenuItem("4:3");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.getMediaPlayer().setCropGeometry("4:3");
			}
		});
		mnGeometriasize.add(menuItem);
		
		mntmNewMenuItem = new JMenuItem("16:9");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.getMediaPlayer().setCropGeometry("16:4");
			}
		});
		mnGeometriasize.add(mntmNewMenuItem);
		
		menuItem_1 = new JMenuItem("5:4");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.getMediaPlayer().setCropGeometry("5:4");
			}
		});
		mnGeometriasize.add(menuItem_1);
		
		mntmCargardvd = new JMenuItem("CargarDVD");
		mntmCargardvd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarDvd();
			}
		});
		mnVideo.add(mntmCargardvd);
	}

}
