package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import fr.univcotedazur.polytech.si4.fsm.project.basiccoffeecontroller.BasicCoffeeControllerStatemachine;
import fr.univcotedazur.polytech.si4.fsm.project.basiccoffeecontroller.IBasicCoffeeControllerStatemachine;
import fr.univcotedazur.polytech.si4.fsm.project.products.*;
import fr.univcotedazur.polytech.si4.fsm.project.user.Info;

public class DrinkFactoryMachine extends JFrame {
	protected static BasicCoffeeControllerStatemachine theFSM;
	protected Product choice, finalChoice;
	protected final Product NONE = new None();
	protected long money;
	protected int size, temperature, nbSugar;
	protected int change;
	protected boolean recipeStarted = false, nfcFree = false;
	protected String consoleMessage;
	protected HashMap<Integer, Info> nfcMap = new HashMap<>();
	JLabel messagesToUser, lblChange, lblSugar, lblTemperature;
	JSlider sizeSlider, temperatureSlider, sugarSlider;
	JCheckBox checkLait, checkCroutons, checkSirop, checkGlace;
	JTextField nfcInput;
	float progressBarValue;
	int stopTimer;
	JProgressBar progressBar;
	JButton coffeeButton, teaButton, expressoButton, soupButton, icedTeaButton;
	JLabel labelForPictures;

	Thread t;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2030629304432075314L;
	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=311,475
	 */
	private final ImageIcon imageIcon = new ImageIcon();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			try {
				theFSM = new BasicCoffeeControllerStatemachine();
				TimerService timer = new TimerService();
				theFSM.setTimer(timer);

				IBasicCoffeeControllerStatemachine.SCInterfaceOperationCallback callback = new IBasicCoffeeControllerStatemachine.SCInterfaceOperationCallback() {
					@Override
					public boolean isCoffee() {
						return theFSM.getChoice().equals("coffee");
					}

					@Override
					public boolean isTea() {
						return theFSM.getChoice().equals("tea");
					}

					@Override
					public boolean isExpresso() {
						return theFSM.getChoice().equals("expresso");
					}

					@Override
					public boolean isSoup() {
						return theFSM.getChoice().equals("soup");
					}

					@Override
					public boolean isIceTea() {
						return theFSM.getChoice().equals("icetea");
					}
				};
				theFSM.getSCInterface().setSCInterfaceOperationCallback(callback);

				theFSM.init();
				theFSM.enter();

				DrinkFactoryMachine frame = new DrinkFactoryMachine();
				frame.setVisible(true);

				theFSM.getSCInterface().getListeners().add(new CoffeeMachineControlerInterfaceImplementation(frame));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DrinkFactoryMachine() {
		Runnable r = () -> {
			while(true) {
				theFSM.runCycle();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		};
		t = new Thread(r);
		t.start();

		progressBarValue=0;
		stopTimer=0;
		consoleMessage = "<html>Welcome<br>You may take order";
		money = 0;
		choice = NONE;
		
		theFSM.setCoffeeStock(1);
		theFSM.setTeaStock(4);
		theFSM.setExpressoStock(7);
		theFSM.setSoupStock(4);
		theFSM.setIceTeaStock(2);

		theFSM.setLaitStock(1);
		theFSM.setSiropStock(5);
		theFSM.setGlaceStock(3);
		theFSM.setEpiceStock(6);
		theFSM.setSucreStock(7);
		theFSM.setCroutonStock(3);

		setForeground(Color.WHITE);
		setFont(new Font("Cantarell", Font.BOLD, 22));
		setBackground(Color.DARK_GRAY);
		setTitle("Drinking Factory Machine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		messagesToUser = new JLabel(consoleMessage);
		messagesToUser.setForeground(Color.WHITE);
		messagesToUser.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUser.setVerticalAlignment(SwingConstants.TOP);
		messagesToUser.setToolTipText("message to the user");
		messagesToUser.setBackground(Color.WHITE);
		messagesToUser.setBounds(126, 34, 165, 70);
		contentPane.add(messagesToUser);

		checkLait = new JCheckBox();
		checkLait.setText("Nuage de lait [" + theFSM.getLaitStock() + "]");
		checkLait.setBounds(126, 100, 165, 25);
		checkLait.setBackground(Color.DARK_GRAY);
		checkLait.setForeground(Color.WHITE);
		checkLait.addActionListener(e -> {
			if (checkLait.isSelected()) {
				theFSM.setLait(1);
			}
			else {
				theFSM.setLait(0);
			}

		});
		checkLait.setVisible(false);
		checkLait.setEnabled(false);
		contentPane.add(checkLait);

		checkCroutons = new JCheckBox();
		checkCroutons.setText("Croutons [" + theFSM.getCroutonStock() + "]");
		checkCroutons.setBounds(126, 125, 165, 25);
		checkCroutons.setBackground(Color.DARK_GRAY);
		checkCroutons.setForeground(Color.WHITE);
		checkCroutons.addActionListener(e -> {
			if (checkCroutons.isSelected()) {
				theFSM.setCroutons(1);
			}
			else {
				theFSM.setCroutons(0);
			}
		});
		checkCroutons.setVisible(false);
		checkCroutons.setEnabled(false);
		contentPane.add(checkCroutons);

		checkSirop = new JCheckBox();
		checkSirop.setText("Sirop d'érable [" + theFSM.getSiropStock() + "]");
		checkSirop.setBounds(126, 150, 165, 25);
		checkSirop.setBackground(Color.DARK_GRAY);
		checkSirop.setForeground(Color.WHITE);
		checkSirop.addActionListener(e -> {
			if (checkSirop.isSelected()) {
				theFSM.setSirop(1);
				sugarSlider.setVisible(false);
				lblSugar.setVisible(false);
			}
			else {
				theFSM.setSirop(0);
				sugarSlider.setVisible(true);
				lblSugar.setVisible(true);
			}
		});
		checkSirop.setVisible(false);
		checkSirop.setEnabled(false);
		contentPane.add(checkSirop);

		checkGlace = new JCheckBox();
		checkGlace.setText("Glace vanille [" + theFSM.getGlaceStock() + "]");
		checkGlace.setBounds(126, 175, 165, 25);
		checkGlace.setBackground(Color.DARK_GRAY);
		checkGlace.setForeground(Color.WHITE);
		checkGlace.addActionListener(e -> {
			if (checkGlace.isSelected()) {
				theFSM.setGlace(1);
			}
			else {
				theFSM.setGlace(0);
			}
		});
		checkGlace.setVisible(false);
		checkGlace.setEnabled(false);
		contentPane.add(checkGlace);

		JLabel lblCoins = new JLabel("Coins");
		lblCoins.setForeground(Color.WHITE);
		lblCoins.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoins.setBounds(538, 12, 44, 15);
		contentPane.add(lblCoins);

		coffeeButton = new JButton(theFSM.getCoffeeStock() + " Coffee");
		coffeeButton.setForeground(Color.WHITE);
		coffeeButton.setBackground(Color.DARK_GRAY);
		coffeeButton.setBounds(12, 34, 96, 25);
		coffeeButton.addActionListener(actionEvent -> updateChoice(new Coffee()));
		contentPane.add(coffeeButton);
		
		
		expressoButton = new JButton(theFSM.getExpressoStock() + " Expresso");
		expressoButton.setForeground(Color.WHITE);
		expressoButton.setBackground(Color.DARK_GRAY);
		expressoButton.setBounds(12, 71, 96, 25);
		expressoButton.addActionListener(actionEvent -> updateChoice(new Expresso()));
		contentPane.add(expressoButton);

		teaButton = new JButton(theFSM.getTeaStock() + " Tea");
		teaButton.setForeground(Color.WHITE);
		teaButton.setBackground(Color.DARK_GRAY);
		teaButton.setBounds(12, 108, 96, 25);
		teaButton.addActionListener(actionEvent -> updateChoice(new Tea()));
		contentPane.add(teaButton);

		soupButton = new JButton(theFSM.getSoupStock() + " Soup");
		soupButton.setForeground(Color.WHITE);
		soupButton.setBackground(Color.DARK_GRAY);
		soupButton.setBounds(12, 145, 96, 25);
		soupButton.addActionListener(actionEvent -> updateChoice(new Soup()));
		contentPane.add(soupButton);

		icedTeaButton = new JButton(theFSM.getIceTeaStock() + " Ice Tea");
		icedTeaButton.setForeground(Color.WHITE);
		icedTeaButton.setBackground(Color.DARK_GRAY);
		icedTeaButton.setBounds(12, 182, 96, 25);
		icedTeaButton.addActionListener(actionEvent -> updateChoice(new IceTea()));
		contentPane.add(icedTeaButton);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue((int)progressBarValue);
		progressBar.setForeground(Color.LIGHT_GRAY);
		progressBar.setBackground(Color.DARK_GRAY);
		progressBar.setBounds(12, 254, 622, 26);
		contentPane.add(progressBar);

		sugarSlider = new JSlider();
		sugarSlider.setValue(1);
		sugarSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sugarSlider.setBackground(Color.DARK_GRAY);
		sugarSlider.setForeground(Color.WHITE);
		sugarSlider.setPaintTicks(true);
		sugarSlider.setMinorTickSpacing(1);
		sugarSlider.setMajorTickSpacing(1);
		sugarSlider.setMaximum(4);
		sugarSlider.addChangeListener(changeEvent -> {
			theFSM.raiseAny();
			if (choice.isSoup()) {
				theFSM.setEpice(sugarSlider.getValue());
			}
		});
		sugarSlider.setBounds(301, 51, 200, 36);
		contentPane.add(sugarSlider);

		sizeSlider = new JSlider();
		sizeSlider.setPaintTicks(true);
		sizeSlider.setValue(1);
		sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizeSlider.setBackground(Color.DARK_GRAY);
		sizeSlider.setForeground(Color.WHITE);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMaximum(2);
		sizeSlider.addChangeListener(changeEvent -> {
			theFSM.raiseAny();
			if (choice.isIceTea()) {
				if (sizeSlider.getValue() == 0) {
					choice = new IceTea(50);
					theFSM.setPrice(choice.price);
				}
				if (sizeSlider.getValue() == 1) {
					choice = new IceTea(75);
					theFSM.setPrice(choice.price);
				}
			}
		});
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(301, 125, 200, 36);
		contentPane.add(sizeSlider);

		temperatureSlider = new JSlider();
		temperatureSlider.setPaintLabels(true);
		temperatureSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		temperatureSlider.setValue(2);
		temperatureSlider.setBackground(Color.DARK_GRAY);
		temperatureSlider.setForeground(Color.WHITE);
		temperatureSlider.setPaintTicks(true);
		temperatureSlider.setMajorTickSpacing(1);
		temperatureSlider.setMaximum(3);
		temperatureSlider.addChangeListener((changeEvent -> theFSM.raiseAny()));
		temperatureSlider.setBounds(301, 188, 200, 54);

		Hashtable<Integer, JLabel> temperatureTable = new Hashtable<>();
		temperatureTable.put(0, new JLabel("20°C"));
		temperatureTable.put(1, new JLabel("35°C"));
		temperatureTable.put(2, new JLabel("60°C"));
		temperatureTable.put(3, new JLabel("85°C"));
		for (JLabel l : temperatureTable.values()) {
			l.setForeground(Color.WHITE);
		}
		temperatureSlider.setLabelTable(temperatureTable);

		contentPane.add(temperatureSlider);

		lblSugar = new JLabel("Sugar");
		lblSugar.setForeground(Color.WHITE);
		lblSugar.setBackground(Color.DARK_GRAY);
		lblSugar.setHorizontalAlignment(SwingConstants.CENTER);
		lblSugar.setBounds(380, 34, 44, 15);
		contentPane.add(lblSugar);

		JLabel lblSize = new JLabel("Size");
		lblSize.setForeground(Color.WHITE);
		lblSize.setBackground(Color.DARK_GRAY);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setBounds(380, 105, 44, 15);
		contentPane.add(lblSize);

		lblTemperature = new JLabel("Temperature");
		lblTemperature.setForeground(Color.WHITE);
		lblTemperature.setBackground(Color.DARK_GRAY);
		lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemperature.setBounds(363, 173, 96, 15);
		contentPane.add(lblTemperature);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		lblCoins.setLabelFor(panel);
		panel.setBounds(538, 25, 96, 97);
		contentPane.add(panel);

		JButton money50centsButton = new JButton("0.50 €");
		money50centsButton.setForeground(Color.WHITE);
		money50centsButton.setBackground(Color.DARK_GRAY);
		money50centsButton.addActionListener(actionEvent -> updateMoney(50));
		panel.add(money50centsButton);

		JButton money25centsButton = new JButton("0.25 €");
		money25centsButton.setForeground(Color.WHITE);
		money25centsButton.setBackground(Color.DARK_GRAY);
		money25centsButton.addActionListener(actionEvent -> updateMoney(25));
		panel.add(money25centsButton);

		JButton money10centsButton = new JButton("0.10 €");
		money10centsButton.setForeground(Color.WHITE);
		money10centsButton.setBackground(Color.DARK_GRAY);
		money10centsButton.addActionListener(actionEvent -> updateMoney(10));
		panel.add(money10centsButton);

		JLabel lblNfc = new JLabel("NFC");
		lblNfc.setForeground(Color.WHITE);
		lblNfc.setHorizontalAlignment(SwingConstants.CENTER);
		lblNfc.setBounds(541, 135, 41, 15);
		contentPane.add(lblNfc);

		nfcInput = new JTextField();
		nfcInput.setBounds(550, 154, 70, 20);
		contentPane.add(nfcInput);

		nfcMap.put(0, new Info(9, 450));

		JButton nfcButton = new JButton("biiip");
		nfcButton.setForeground(Color.WHITE);
		nfcButton.setBackground(Color.DARK_GRAY);
		nfcButton.setBounds(550, 180, 70, 25);
		nfcButton.addActionListener( actionEvent -> {
			Info currentInfo = nfcMap.get(nfcInput.getText().hashCode());
            nfcInput.setText("");
			theFSM.raiseMoneyGiven();
			theFSM.setNfc(true);
			if (currentInfo != null) {
				if (currentInfo.getCount()==10) {
					nfcFree = true;
				}
				else {
					currentInfo.setCount(currentInfo.getCount()+1);
					currentInfo.setSum((int)(currentInfo.getSum()+choice.price));
				}
			}
			else {
				currentInfo = new Info(1, (int)choice.price);
			}

			nfcMap.put(nfcInput.getText().hashCode(), currentInfo);
		});
		contentPane.add(nfcButton);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 292, 622, 15);
		contentPane.add(separator);

		JButton addCupButton = new JButton("Add cup");
		addCupButton.setForeground(Color.WHITE);
		addCupButton.setBackground(Color.DARK_GRAY);
		addCupButton.setBounds(45, 336, 96, 25);
		contentPane.add(addCupButton);

		lblChange = new JLabel("0");
		lblChange.setForeground(Color.WHITE);
		lblChange.setBackground(Color.DARK_GRAY);
		lblChange.setHorizontalAlignment(SwingConstants.CENTER);
		lblChange.setBounds(450, 560, 200, 15);
		contentPane.add(lblChange);

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelForPictures = new JLabel(new ImageIcon(myPicture));
		labelForPictures.setBounds(175, 319, 286, 260);
		contentPane.add(labelForPictures);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(538, 217, 96, 33);
		contentPane.add(panel_2);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setBackground(Color.DARK_GRAY);
		cancelButton.addActionListener(actionEvent -> theFSM.raiseCancel());
		panel_2.add(cancelButton);

		// listeners
		addCupButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BufferedImage myPicture = null;
				if (!recipeStarted) {
					try {
						theFSM.setOwnCup(1);
						myPicture = ImageIO.read(new File("./picts/ownCup.jpg"));
					} catch (IOException ee) {
						ee.printStackTrace();
					}
					labelForPictures.setIcon(new ImageIcon(myPicture));
				}
			}
		});
		
		labelForPictures.addMouseListener(new MouseAdapter() {
			public void mouseClicked (MouseEvent e) {
				BufferedImage myPicture = null;
				if (!recipeStarted) {
					try {
						theFSM.raiseTakeOrder();
						theFSM.setOwnCup(0);
						myPicture = ImageIO.read(new File("./picts/vide2.jpg"));

					} catch (IOException ee) {
						ee.printStackTrace();
					}
					labelForPictures.setIcon(new ImageIcon(myPicture));
				}
			}
		});
		
		lblChange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				change = 0;
				lblChange.setText(String.valueOf(change));
			}
		});
	}

	void updateConsole(){
		consoleMessage = "<html>Your choice : " + choice.toString() + "<br>" + "Money : " + theFSM.getMoney();
		JLabel console = (JLabel)(contentPane.getComponent(0));
		console.setText(consoleMessage);
	}
	
	void makeDrink() {
		money-=finalChoice.price - theFSM.getOwnCup()*10;
		theFSM.setMoney(money);
		theFSM.raiseAny();
	}

	void updateChoice(Product p) {
		if (!recipeStarted) {
			choice = p;
			theFSM.setPrice(p.price);

			updateConsole();

			theFSM.setChoice(p.name);

			if (choice.isSoup()) {
				lblSugar.setText("Spice");
				if (theFSM.getEpiceStock()==0) {
					sugarSlider.setEnabled(false);
					sugarSlider.setValue(0);
				}
				else {
					if (theFSM.getEpiceStock() < 4) {
						sugarSlider.setMaximum((int)theFSM.getEpiceStock());
						sugarSlider.setEnabled(true);
						sugarSlider.setValue(1);
					}
					else {
						sugarSlider.setMaximum(4);
						sugarSlider.setEnabled(true);
						sugarSlider.setValue(1);
					}
				}
			}
			if (choice.isIceTea()) {
				Hashtable<Integer, JLabel> temperatureTable = new Hashtable<>();
				temperatureTable.put(0, new JLabel("2°C"));
				temperatureTable.put(1, new JLabel("6°C"));
				temperatureTable.put(2, new JLabel("10°C"));
				temperatureTable.put(3, new JLabel("15°C"));
				for (JLabel l : temperatureTable.values()) {
					l.setForeground(Color.WHITE);
				}
				temperatureSlider.setLabelTable(temperatureTable);

				sizeSlider.setMaximum(1);
				sizeSlider.setValue(0);
			}
			else {
				Hashtable<Integer, JLabel> temperatureTable = new Hashtable<>();
				temperatureTable.put(0, new JLabel("20°C"));
				temperatureTable.put(1, new JLabel("35°C"));
				temperatureTable.put(2, new JLabel("60°C"));
				temperatureTable.put(3, new JLabel("85°C"));
				for (JLabel l : temperatureTable.values()) {
					l.setForeground(Color.WHITE);
				}
				temperatureSlider.setLabelTable(temperatureTable);
			}

			theFSM.raiseChose();
			theFSM.raiseAny();
		}
	}

	void updateMoney(long amount) {
		if (!recipeStarted) {
			theFSM.setMoney(this.money + amount);
			this.money += amount;
			updateConsole();
			theFSM.raiseMoneyGiven();
			theFSM.raiseAny();
		}
	}

	void updateSliders() {
		if (recipeStarted) {
			theFSM.setSucre(this.nbSugar);
			theFSM.setTaille(this.size);
			theFSM.setTemperature(this.temperature);
		}
	}

	public int computePrice() {
		return (int)(theFSM.getPrice()
				- (10*theFSM.getOwnCup())
				+ (10*theFSM.getLait())
				+ (30*theFSM.getCroutons())
				+ (10*theFSM.getSirop())
				+ (60*theFSM.getGlace()));
	}

	public int computeChange() {
		if (!theFSM.getNfc()) {
			return change + (int)(theFSM.getMoney() - computePrice());
		}
		else {
			theFSM.setNfc(false);
			return change + (int)(theFSM.getMoney());
		}

	}

	void hideOptions() {
		checkLait.setVisible(false);
		checkLait.setEnabled(false);
		checkLait.setSelected(false);

		checkCroutons.setEnabled(false);
		checkCroutons.setVisible(false);
		checkCroutons.setSelected(false);

		checkGlace.setVisible(false);
		checkGlace.setVisible(false);
		checkGlace.setSelected(false);

		checkSirop.setVisible(false);
		checkSirop.setEnabled(false);
		checkSirop.setSelected(false);
	}

	void updateOptions() {
		switch (choice.name) {
			case "coffee":
				checkLait.setText("Lait [" + theFSM.getLaitStock() + "]");
				if (theFSM.getLaitStock()==0) {
					checkLait.setVisible(true);
					checkLait.setEnabled(false);
				}
				else {
					checkLait.setVisible(true);
					checkLait.setEnabled(true);
				}

				checkSirop.setText("Sirop d'érable [" + theFSM.getSiropStock() + "]");
				if (theFSM.getSiropStock()==0) {
					checkSirop.setVisible(true);
					checkSirop.setEnabled(false);
				}
				else {
					checkSirop.setVisible(true);
					checkSirop.setEnabled(true);
				}

				checkGlace.setText("Glace vanille [" + theFSM.getGlaceStock() + "]");
				if (theFSM.getGlaceStock()==0) {
					checkGlace.setVisible(true);
					checkGlace.setEnabled(false);
				}
				else {
					checkGlace.setVisible(true);
					checkGlace.setEnabled(true);
				}

				lblSugar.setText("Sugar");
				lblSugar.setVisible(true);

				sizeSlider.setMaximum(2);
				sizeSlider.setValue(1);
				break;

			case "tea":
				checkLait.setText("Lait [" + theFSM.getLaitStock() + "]");
				if (theFSM.getLaitStock()==0) {
					checkLait.setVisible(true);
					checkLait.setEnabled(false);
				}
				else {
					checkLait.setVisible(true);
					checkLait.setEnabled(true);
				}

				checkSirop.setText("Sirop d'érable [" + theFSM.getSiropStock() + "]");
				if (theFSM.getSiropStock()==0) {
					checkSirop.setVisible(true);
					checkSirop.setEnabled(false);
				}
				else {
					checkSirop.setVisible(true);
					checkSirop.setEnabled(true);
				}

				lblSugar.setText("Sugar");
				lblSugar.setVisible(true);

				sizeSlider.setMaximum(2);
				sizeSlider.setValue(1);
				break;

			case "expresso":
				checkSirop.setText("Sirop d'érable [" + theFSM.getSiropStock() + "]");
				if (theFSM.getSiropStock()==0) {
					checkSirop.setVisible(true);
					checkSirop.setEnabled(false);
				}
				else {
					checkSirop.setVisible(true);
					checkSirop.setEnabled(true);
				}

				checkGlace.setText("Glace vanille [" + theFSM.getGlaceStock() + "]");
				if (theFSM.getGlaceStock()==0) {
					checkGlace.setVisible(true);
					checkGlace.setEnabled(false);
				}
				else {
					checkGlace.setVisible(true);
					checkGlace.setEnabled(true);
				}

				lblSugar.setText("Sugar");
				lblSugar.setVisible(true);

				sizeSlider.setMaximum(2);
				sizeSlider.setValue(1);
				break;

			case "soup":
				checkCroutons.setText("Croutons [" + theFSM.getCroutonStock() + "]");
				if(theFSM.getCroutonStock()==0) {
					checkCroutons.setVisible(true);
					checkCroutons.setEnabled(false);
				}
				else {
					checkCroutons.setVisible(true);
					checkCroutons.setEnabled(true);
				}

				lblSugar.setVisible(true);

				sizeSlider.setMaximum(2);
				sizeSlider.setValue(1);
				break;

			case "icetea":
				checkSirop.setText("Sirop d'érable [" + theFSM.getSiropStock() + "]");
				if (theFSM.getSiropStock()==0) {
					checkSirop.setVisible(true);
					checkSirop.setEnabled(false);
				}
				else {
					checkSirop.setVisible(true);
					checkSirop.setEnabled(true);
				}

				lblSugar.setText("Sugar");
				lblSugar.setVisible(true);
				break;
			default:
				break;
		}
	}

	void reset() {
		System.out.println(theFSM.getSucreStock());

		updateSugarSlider();

		sizeSlider.setMaximum(2);
		sizeSlider.setValue(1);

		temperatureSlider.setValue(2);
		Hashtable<Integer, JLabel> temperatureTable = new Hashtable<>();
		temperatureTable.put(0, new JLabel("20°C"));
		temperatureTable.put(1, new JLabel("35°C"));
		temperatureTable.put(2, new JLabel("60°C"));
		temperatureTable.put(3, new JLabel("85°C"));
		for (JLabel l : temperatureTable.values()) {
			l.setForeground(Color.WHITE);
		}
		temperatureSlider.setLabelTable(temperatureTable);

		hideOptions();
		theFSM.setLait(0);
		theFSM.setSirop(0);
		theFSM.setCroutons(0);
		theFSM.setGlace(0);

		consoleMessage = "<html>Welcome<br>You may take order";
		messagesToUser.setText(consoleMessage);

		coffeeButton.setText(theFSM.getCoffeeStock() + " Coffee");
		if(theFSM.getCoffeeStock()==0) {
			coffeeButton.setEnabled(false);
		}
		teaButton.setText(theFSM.getTeaStock() + " Tea");
		if(theFSM.getTeaStock()==0) {
			teaButton.setEnabled(false);
		}
		expressoButton.setText(theFSM.getExpressoStock() + " Expresso");
		if(theFSM.getExpressoStock()==0) {
			expressoButton.setEnabled(false);
		}
		soupButton.setText(theFSM.getSoupStock() + " Soup");
		if(theFSM.getSoupStock()==0) {
			soupButton.setEnabled(false);
		}
		icedTeaButton.setText(theFSM.getIceTeaStock() + " Ice Tea");
		if(theFSM.getIceTeaStock()==0) {
			icedTeaButton.setEnabled(false);
		}
		checkLait.setText("Lait [" + theFSM.getLaitStock() + "]");
		if (theFSM.getLaitStock()==0) {
			checkLait.setEnabled(false);
		}
		checkSirop.setText("Sirop d'érable [" + theFSM.getSiropStock() + "]");
		if (theFSM.getSiropStock()==0) {
			checkSirop.setEnabled(false);
		}
		checkGlace.setText("Glace vanille [" + theFSM.getGlaceStock() + "]");
		if (theFSM.getGlaceStock()==0) {
			checkGlace.setEnabled(false);
		}
		checkCroutons.setText("Croutons [" + theFSM.getCroutonStock() + "]");
		if(theFSM.getCroutonStock()==0) {
			checkCroutons.setEnabled(false);
		}

		money = 0;
		theFSM.setMoney(0);

		theFSM.setEpice(-1);
		lblSugar.setText("Sugar");
		lblSugar.setVisible(true);
	}

	public void updateSugarSlider() {
		sugarSlider.setVisible(true);
		if (theFSM.getSucreStock()==0) {
			sugarSlider.setEnabled(false);
			sugarSlider.setValue(0);
		}
		else {
			if (theFSM.getSucreStock() < 4) {
				sugarSlider.setMaximum((int)theFSM.getSucreStock());
				sugarSlider.setEnabled(true);
				sugarSlider.setValue(1);
			}
			else {
				sugarSlider.setEnabled(true);
				sugarSlider.setValue(1);
			}
		}
	}

	public void updateEpiceSlider() {
		sugarSlider.setVisible(true);
		if (theFSM.getEpiceStock()==0) {
			sugarSlider.setEnabled(false);
			sugarSlider.setValue(0);
		}
		else {
			if (theFSM.getEpiceStock() < 4) {
				sugarSlider.setMaximum((int)theFSM.getEpiceStock());
				sugarSlider.setEnabled(true);
				sugarSlider.setValue(1);
			}
			else {
				sugarSlider.setEnabled(true);
				sugarSlider.setValue(1);
			}
		}
	}

	public boolean hasOwnCup() {
		return theFSM.getOwnCup() == 1;
	}
}
