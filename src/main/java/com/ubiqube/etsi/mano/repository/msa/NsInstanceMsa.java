package com.ubiqube.etsi.mano.repository.msa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.api.interfaces.repository.RepositoryService;
import com.ubiqube.etsi.mano.model.nslcm.sol005.NsInstancesNsInstance;

@Service
public class NsInstanceMsa extends AbstractGenericRepository<NsInstancesNsInstance> implements com.ubiqube.etsi.mano.repository.NsInstanceRepository {
	private static final String REPOSITORY_NS_INSTANCE_DATAFILE_BASE_PATH = "Datafiles/NFVO/ns_instances";

	public NsInstanceMsa(ObjectMapper _mapper, RepositoryService _repositoryService) {
		super(_mapper, _repositoryService);
	}

	@Override
	String getUriForId(String _id) {
		return REPOSITORY_NS_INSTANCE_DATAFILE_BASE_PATH + '/' + _id + "/nsInstance.json";
	}

	@Override
	String setId(NsInstancesNsInstance _entity) {
		final String id = _entity.getId();
		if (null == id) {
			_entity.setId(UUID.randomUUID().toString());
		}

		return _entity.getId();
	}

	@Override
	Class<?> getClazz() {
		return NsInstancesNsInstance.class;
	}

	@Override
	public List<NsInstancesNsInstance> query() {
		return new ArrayList<>();
	}

}
