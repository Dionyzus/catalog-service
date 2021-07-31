package com.odak.catalogservice.utils.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.odak.catalogservice.model.CatalogItem;

public class CatalogItemSorter {

	private CatalogItemSorter() {
		throw new UnsupportedOperationException("Utils class instantiation not allowed.");
	}

	public static void sort(List<CatalogItem> catalogItems, String sortField, String sortDirection) {

		if ("id".equalsIgnoreCase(sortField)) {
			doSort(catalogItems, sortDirection, new IdComparator());
		} else if ("name".equalsIgnoreCase(sortField)) {
			doSort(catalogItems, sortDirection, new NameComparator());
		} else if ("price".equalsIgnoreCase(sortField)) {
			doSort(catalogItems, sortDirection, new PriceComparator());
		}
	}

	private static void doSort(List<CatalogItem> catalogItems, String sortDirection,
			Comparator<CatalogItem> comparator) {
		if ("desc".equalsIgnoreCase(sortDirection)) {
			Collections.sort(catalogItems, Collections.reverseOrder(comparator));
		} else if ("asc".equalsIgnoreCase(sortDirection)) {
			Collections.sort(catalogItems, comparator);
		}
	}

	static class IdComparator implements Comparator<CatalogItem> {
		@Override
		public int compare(CatalogItem o1, CatalogItem o2) {
			return o1.getId().compareToIgnoreCase(o2.getId());
		}
	}

	static class NameComparator implements Comparator<CatalogItem> {
		@Override
		public int compare(CatalogItem o1, CatalogItem o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	}

	static class PriceComparator implements Comparator<CatalogItem> {
		@Override
		public int compare(CatalogItem o1, CatalogItem o2) {
			double price1 = o1.getPrice();
			double price2 = o2.getPrice();

			if (price1 == price2)
				return 0;
			else if (price1 > price2)
				return 1;
			else
				return -1;
		}
	}
}
