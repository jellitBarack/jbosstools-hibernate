/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.hibernate.eclipse.graph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import org.eclipse.swt.graphics.Image;
import org.hibernate.eclipse.console.HibernateConsolePlugin;
import org.hibernate.eclipse.console.workbench.HibernateWorkbenchHelper;
import org.jboss.tools.hibernate.runtime.spi.IProperty;
import org.jboss.tools.hibernate.runtime.spi.IType;
import org.jboss.tools.hibernate.runtime.spi.IValue;

public class PropertyViewAdapter extends Observable {

	final private IProperty property;

	private final ConfigurationViewAdapter configuration;

	private final PersistentClassViewAdapter clazz;

	private List<PropertyAssociationViewAdapter> sourceAssociations;
	private List<PropertyViewAdapter> targetAssociations;
	
	public PropertyViewAdapter(PersistentClassViewAdapter clazz,
			IProperty property) {
		this.clazz = clazz;
		this.property = property;
		this.configuration = clazz.getConfiguration();
		this.sourceAssociations = null;
		this.targetAssociations = Collections.emptyList();
		
	}

	public IProperty getProperty() {
		return property;
	}

	public List<PropertyAssociationViewAdapter> getSourceConnections() {
		checkConnections();
		return sourceAssociations;
	}

	private void checkConnections() {
		if(sourceAssociations==null) {
			sourceAssociations = new ArrayList<PropertyAssociationViewAdapter>();
			createSingleEndedEnityAssociations();
		}		
	}

	public List<PropertyViewAdapter> getTargetConnections() {
		return targetAssociations;
	}
	
	private void createSingleEndedEnityAssociations() {
		try { //TODO: we need the consoleconfiguration here to know the exact types	
			IValue value = property.getValue();
			if ( value != null && value.isCollection() ) {
				if(!value.isInverse() && value.getElement().isOneToMany()) {
					IValue oneToMany = value.getElement();
					
					String entityName = oneToMany.getAssociatedClass().getEntityName();
					PersistentClassViewAdapter target = configuration
					.getPersistentClassViewAdapter( entityName );
					PropertyAssociationViewAdapter pava = new PropertyAssociationViewAdapter( clazz, this, target );
					this.addSourceAssociation( pava );
					target.addTargetAssociation( pava );
				}
			} else if ( property.getType().isEntityType() ) {
				IType et = (IType) property.getType();
				PersistentClassViewAdapter target = configuration.getPersistentClassViewAdapter( et.getAssociatedEntityName() );
				PropertyAssociationViewAdapter pava = new PropertyAssociationViewAdapter( clazz, this, target );
				this.addSourceAssociation( pava );
				target.addTargetAssociation( pava );
			}
		} catch(Exception he) {
			HibernateConsolePlugin.getDefault().logWarning( he );
		}
		
		}
		

	private void addSourceAssociation(PropertyAssociationViewAdapter pava) {
		checkConnections();
		sourceAssociations.add(pava);
		setChanged();
		notifyObservers(PersistentClassViewAdapter.ASSOCIATONS);
	}
	
	public Image getImage() {
		return HibernateWorkbenchHelper.getImage(getProperty());
	}
}
