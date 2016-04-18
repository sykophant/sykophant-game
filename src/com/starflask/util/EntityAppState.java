package com.starflask.util;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Entity;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.starflask.MonkeyApplication;
import com.starflask.peripherals.InputActionComponent;

public class EntityAppState extends AbstractAppState{
	
	 private MonkeyApplication app;
	 private Entity ashleyEntity;
	 
	 @Override
	    public void initialize(AppStateManager stateManager, Application app) {
	      super.initialize(stateManager, app); 
	      this.app = (MonkeyApplication)app;          // cast to a more specific class
	      
	      ashleyEntity = new Entity();
	      this.app.getECS().addEntity(ashleyEntity);
	   }
	 
	 
	 protected void addComponent(Component c) {
		 ashleyEntity.add(c); 
	}
	 
	
	 public <T extends Component> T getComponent (Class<T> componentClass) {
			return ashleyEntity.getComponent( componentClass );
	}
	 
	 
	 
	 
}
