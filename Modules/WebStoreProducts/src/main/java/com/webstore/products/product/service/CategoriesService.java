/*
 * #%L
 * jVoiD Open Platform
 * %%
 * Copyright 2014-2015 Schogini Systems Pvt Ltd (http://www.schogini.com)
 * Project Website: http://www.jvoid.com
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
 package com.webstore.products.product.service;

import java.util.List;

import com.webstore.products.product.model.Categories;

public interface CategoriesService {

	public void addCategories(Categories p);
	public void updateCategories(Categories p);
	public List<Categories> listCategories();
	public Categories getCategoriesById(int id);
	public void removeCategories(int id);
	public List<Categories> getAllChildCategories(int categoryId);
}
