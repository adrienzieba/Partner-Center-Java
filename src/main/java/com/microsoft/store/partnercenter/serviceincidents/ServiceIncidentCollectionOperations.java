// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.serviceincidents;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.store.partnercenter.BasePartnerComponentString;
import com.microsoft.store.partnercenter.IPartner;
import com.microsoft.store.partnercenter.PartnerService;
import com.microsoft.store.partnercenter.exception.PartnerErrorCategory;
import com.microsoft.store.partnercenter.exception.PartnerException;
import com.microsoft.store.partnercenter.models.ResourceCollection;
import com.microsoft.store.partnercenter.models.query.IQuery;
import com.microsoft.store.partnercenter.models.serviceincidents.ServiceIncidents;
import com.microsoft.store.partnercenter.models.utils.KeyValuePair;

/**
 * Service incident collection operations implementation class.
 */
public class ServiceIncidentCollectionOperations 
		extends BasePartnerComponentString
		implements IServiceIncidentCollection
{

	/**
	 * Initializes a new instance of the ServiceIncidentCollectionOperations class.
	 * 
	 * @param rootPartnerOperations The root partner operations instance.
	 */
	public ServiceIncidentCollectionOperations(IPartner rootPartnerOperations)
	{
		super(rootPartnerOperations);
	}

	/**
	 * Gets the list of service incidents.
	 */
	@Override
	public ResourceCollection<ServiceIncidents> get()
	{
		return this.getPartner().getServiceClient().get(
			this.getPartner(),
			new TypeReference<ResourceCollection<ServiceIncidents>>(){}, 
			MessageFormat.format(
				PartnerService.getInstance().getConfiguration().getApis().get("GetServiceIncidents").getPath(),
				this.getContext()));
	}

	/**
	 * Retrieves all service incidents.
	 * 
	 * @param serviceIncidentsQuery A query to retrieve service incidents based on the active status.
	 * @return The list of service incidents.
	 */
	@Override
	public ResourceCollection<ServiceIncidents> get(IQuery serviceIncidentsQuery)
	{
		if (serviceIncidentsQuery == null)
		{
			throw new IllegalArgumentException("serviceIncidentsQuery can't be null");
		}
		
		Collection<KeyValuePair<String, String>> parameters = new ArrayList<KeyValuePair<String, String>>();

		if (serviceIncidentsQuery.getFilter() != null)
		{
			// add the filter to the request if specified
			ObjectMapper mapper = new ObjectMapper();

			try
			{
				parameters.add
				(
					new KeyValuePair<String, String>(
						PartnerService.getInstance().getConfiguration().getApis().get("SearchPartnerServiceRequests").getParameters().get("Filter"),
						URLEncoder.encode(mapper.writeValueAsString(serviceIncidentsQuery.getFilter()),
						"UTF-8"))
				);
			}
			catch (UnsupportedEncodingException e)
			{
				throw new PartnerException("", null, PartnerErrorCategory.REQUEST_PARSING, e);
			}
			catch (JsonProcessingException e)
			{
				throw new PartnerException("", null, PartnerErrorCategory.REQUEST_PARSING, e);
			}
		}
		
		return this.getPartner().getServiceClient().get(
			this.getPartner(),
			new TypeReference<ResourceCollection<ServiceIncidents>>(){}, 
			PartnerService.getInstance().getConfiguration().getApis().get("GetServiceIncidents").getPath(),
			parameters);
	}
}