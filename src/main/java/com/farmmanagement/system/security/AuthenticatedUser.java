package com.farmmanagement.system.security;

import com.farmmanagement.system.model.Role;

/**
 * Lightweight representation of the authenticated user stored in the security context.
 */
public record AuthenticatedUser(String id, String email, Role role) {
}
