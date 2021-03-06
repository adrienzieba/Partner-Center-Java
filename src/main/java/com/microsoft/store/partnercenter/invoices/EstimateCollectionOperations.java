// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license. See the LICENSE file in the project root for full license information.

package com.microsoft.store.partnercenter.invoices;

import com.microsoft.store.partnercenter.BasePartnerComponentString;
import com.microsoft.store.partnercenter.IPartner;

/**
 * Defines the operations available for estimate collection.
 */
public class EstimateCollectionOperations 
    extends BasePartnerComponentString
    implements IEstimateCollection 
{
    /**
     * Initializes a new instance of the EstimateCollectionOperations class.
     * 
     * @param rootPartnerOperations The root partner operations instance.
     */
    public EstimateCollectionOperations(IPartner rootPartnerOperations)
    {
        super(rootPartnerOperations);
    }

    /**
     * Gets the estimate links of the recon line items.
     * 
     * @return The estimate links of the recon line items.
     */
    @Override
    public IEstimateLink getLinks() 
    {
        return new EstimateLinkOperations(this.getPartner());
    }
}