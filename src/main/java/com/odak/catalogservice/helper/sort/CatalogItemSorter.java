package com.odak.catalogservice.helper.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.CatalogItem;

public class CatalogItemSorter {

	private CatalogItemSorter() {
		throw new UnsupportedOperationException("Utils class instantiation not allowed.");
	}

	public static List<CatalogItem> sort(List<CatalogItem> catalogItems, String sortDirection,
			Comparator<CatalogItem> comparator) {

		List<CatalogItem> sortedList = catalogItems.stream().collect(Collectors.toList());

		if (SortDirection.DESC.toString().equalsIgnoreCase(sortDirection)) {

			Collections.sort(sortedList, Collections.reverseOrder(comparator));
			return sortedList;

		} else {

			Collections.sort(sortedList, comparator);
			return sortedList;
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
