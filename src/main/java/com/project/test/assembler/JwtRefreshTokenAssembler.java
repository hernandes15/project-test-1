package com.project.test.assembler;



import com.project.test.model.dto.JwtRefreshToken;
import com.project.test.model.entity.JwtRefreshTokenEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRefreshTokenAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public JwtRefreshToken convertToDto(JwtRefreshTokenEntity jwtRefreshTokenEntity){
        if (jwtRefreshTokenEntity == null){
            return null;
        }
        JwtRefreshToken jwtRefreshToken = modelMapper.map(jwtRefreshTokenEntity, JwtRefreshToken.class);

        return jwtRefreshToken;
    }

    public List<JwtRefreshToken> convertToDtos(List<JwtRefreshTokenEntity> jwtRefreshTokenEntities){
        return jwtRefreshTokenEntities.stream()
                .map(entity -> convertToDto(entity))
                .collect(Collectors.toList());
    }

    public JwtRefreshTokenEntity convertToEntity(JwtRefreshToken jwtRefreshToken){
        JwtRefreshTokenEntity jwtRefreshTokenEntity = modelMapper.map(jwtRefreshToken, JwtRefreshTokenEntity.class);
        return jwtRefreshTokenEntity;
    }

}
