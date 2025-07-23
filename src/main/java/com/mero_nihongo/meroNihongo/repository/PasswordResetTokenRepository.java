package com.mero_nihongo.meroNihongo.repository;

import com.mero_nihongo.meroNihongo.model.PasswordResetToken;
import com.mero_nihongo.meroNihongo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
    Optional<PasswordResetToken> findByToken(String token);
    
    Optional<PasswordResetToken> findByUserAndIsUsedFalse(User user);
    
    @Query("SELECT prt FROM PasswordResetToken prt WHERE prt.user = :user AND prt.isUsed = false AND prt.expiresAt > :now")
    Optional<PasswordResetToken> findValidTokenByUser(@Param("user") User user, @Param("now") LocalDateTime now);
    
    @Modifying
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.expiresAt < :now OR prt.isUsed = true")
    void deleteExpiredAndUsedTokens(@Param("now") LocalDateTime now);
    
    @Modifying
    @Query("UPDATE PasswordResetToken prt SET prt.isUsed = true, prt.usedAt = :now WHERE prt.user = :user AND prt.isUsed = false")
    void invalidateAllUserTokens(@Param("user") User user, @Param("now") LocalDateTime now);
}
