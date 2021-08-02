package com.odak.catalogservice.dataloader;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.odak.catalogservice.repository.catalogitem.CatalogItemRepositoryImpl;
import com.odak.catalogservice.util.dummydataloader.CatalogItemDataLoader;

@RunWith(MockitoJUnitRunner.class)
public class CatalogItemDataLoaderTest {

	@MockBean
	CatalogItemRepositoryImpl mockRepository;

	@Mock
	CatalogItemDataLoader commandLineTaskExecutor;

	@Test
	public void whenCommandLine_thenRunnersRun() throws Exception {
		commandLineTaskExecutor.loadCatalogItemData(mockRepository);
		verify(commandLineTaskExecutor, times(1)).loadCatalogItemData(mockRepository);
	}
}