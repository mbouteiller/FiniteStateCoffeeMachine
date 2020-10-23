package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.basiccoffeecontroller.IBasicCoffeeControllerStatemachine.SCInterfaceListener;

public class CoffeeMachineControlerInterfaceImplementation implements SCInterfaceListener {

    DrinkFactoryMachine theDfm;
    public CoffeeMachineControlerInterfaceImplementation(DrinkFactoryMachine dfm) {
        theDfm = dfm;
    }
    
    @Override
    public void onPayRaised() {
    }

    @Override
    public void onRefundRaised() {

    }

    @Override
    public void onGiveChangeRaised() {

    }

    @Override
    public void onCheckAmountRaised() {

    }

    @Override
    public void onCancelRaised() {

    }

    @Override
    public void onStartRecipeRaised() {
        System.out.println("lets go le café lets go");
    }

    @Override
    public void onCleanRaised() {

    }

    @Override
    public void onRestartRaised() {

    }
}
