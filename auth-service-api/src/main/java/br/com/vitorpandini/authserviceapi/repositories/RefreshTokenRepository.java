package br.com.vitorpandini.authserviceapi.repositories;

import br.com.vitorpandini.authserviceapi.models.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken,String> {
}
