package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DTO.UsuarioUpdateDTO;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.EstadoJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.MunicipioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.PaisJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.RolJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            List<UsuarioJPA> usuariosJPA = queryUsuario.getResultList();

            result.object = usuariosJPA;

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        return result;
    }

    @Override
    @Transactional
    public Result AddUsuario(UsuarioJPA usuario) {
        Result result = new Result();

        try {
            entityManager.persist(usuario);

            for (DireccionJPA direccion : usuario.DireccionesJPA) {
                direccion.UsuarioJPA = usuario;
                entityManager.persist(direccion);
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetById(int id) {

        Result result = new Result();
        try {
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, id);
            result.object = usuarioJPA;
        } catch (Exception ex) {
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.object = null;
        }
        return result;
    }

    @Override
    @Transactional
    public Result UpdateUsuario(UsuarioUpdateDTO usuario) {
        Result result = new Result();

        try {

            entityManager.merge(usuario);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
            return result;
    }

    @Override
    @Transactional
    public Result UpdateImgUsuario(UsuarioJPA usuario) {

        Result result = new Result();
        try {

            String query = "UPDATE UsuarioJPA SET FotoUsuario = :newFoto WHERE IdUsuario = :id";

            entityManager.createQuery(query)
                    .setParameter("newFoto", usuario.getFotoUsuario())
                    .setParameter("id", usuario.getIdUsuario())
                    .executeUpdate();

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional
    public Result DeleteUsuario(int id) {
        Result result = new Result();

        try {
            UsuarioJPA usuarioJPA = entityManager.getReference(UsuarioJPA.class, id);
            entityManager.remove(usuarioJPA);
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result AddUsuariosByFile(List<UsuarioJPA> usuarios) {
        Result result = new Result();
        try {
            for (UsuarioJPA usuario : usuarios) {

                entityManager.persist(usuario);
            }
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Override
    public Result GetAllDinamico(UsuarioJPA usuario) {
        Result result = new Result();

        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT u.IdUsuario, u.NombreUsuario, u.ApellidoPatUsuario, u.ApellidoMatUsuario, ");
            jpql.append("u.UserName, u.EmailUsuario, u.FotoUsuario, u.TelefonoUsuario, u.CelularUsuario, u.StatusUsuario, ");
            jpql.append("d.Calle, d.NumeroInterior, d.NumeroExterior, ");
            jpql.append("c.NombreColonia, c.CodigoPostal, m.NombreMunicipio, e.NombreEstado, p.NombrePais ");
            jpql.append("FROM UsuarioJPA u ");
            jpql.append("JOIN u.RolJPA r ");
            jpql.append("JOIN u.DireccionesJPA d ");
            jpql.append("JOIN d.ColoniaJPA c ");
            jpql.append("JOIN c.MunicipioJPA m ");
            jpql.append("JOIN m.EstadoJPA e ");
            jpql.append("JOIN e.PaisJPA p ");
            jpql.append("WHERE 1 = 1 ");

            if (usuario.getNombreUsuario() != null && !usuario.getNombreUsuario().isEmpty()) {
                jpql.append(" AND LOWER(u.NombreUsuario) LIKE LOWER(:nombre) ");
            }
            if (usuario.getApellidoPatUsuario() != null && !usuario.getApellidoPatUsuario().isEmpty()) {
                jpql.append(" AND LOWER(u.ApellidoPatUsuario) LIKE LOWER(:apellidoPat) ");
            }
            if (usuario.getApellidoMatUsuario() != null && !usuario.getApellidoMatUsuario().isEmpty()) {
                jpql.append(" AND LOWER(u.ApellidoMatUsuario) LIKE LOWER(:apellidoMat) ");
            }

            if (usuario.getStatusUsuario() == 0 || usuario.getStatusUsuario() == 1) {
                jpql.append(" AND u.StatusUsuario = :status");
            }

            if (usuario.RolJPA.getIdRol() != 0) {
                jpql.append(" AND r.IdRol = :idRol ");
            }

            jpql.append(" ORDER BY u.IdUsuario ");

            TypedQuery<Object[]> query = entityManager.createQuery(jpql.toString(), Object[].class);

            if (usuario.getNombreUsuario() != null && !usuario.getNombreUsuario().isEmpty()) {
                query.setParameter("nombre", "%" + usuario.getNombreUsuario() + "%");
            }
            if (usuario.getApellidoPatUsuario() != null && !usuario.getApellidoPatUsuario().isEmpty()) {
                query.setParameter("apellidoPat", "%" + usuario.getApellidoPatUsuario() + "%");
            }
            if (usuario.getApellidoMatUsuario() != null && !usuario.getApellidoMatUsuario().isEmpty()) {
                query.setParameter("apellidoMat", "%" + usuario.getApellidoMatUsuario() + "%");
            }
            if (usuario.getStatusUsuario() == 0 || usuario.getStatusUsuario() == 1) {
                query.setParameter("status", usuario.getStatusUsuario());
            }
            if (usuario.RolJPA.getIdRol() != 0) {
                query.setParameter("idRol", usuario.RolJPA.getIdRol());
            }

            List<Object[]> rows = query.getResultList();

            Map<Integer, UsuarioJPA> mapUsuarios = new LinkedHashMap<>();

            for (Object[] row : rows) {

                Integer idUsuario = (Integer) row[0];

                UsuarioJPA u = new UsuarioJPA();

                if (mapUsuarios.containsKey(idUsuario)) {
                    u = mapUsuarios.get(idUsuario);
                } else {
                    u.setIdUsuario(idUsuario);
                    u.setNombreUsuario((String) row[1]);
                    u.setApellidoPatUsuario((String) row[2]);
                    u.setApellidoMatUsuario((String) row[3]);
                    u.setUserName((String) row[4]);
                    u.setEmailUsuario((String) row[5]);
                    u.setFotoUsuario((byte[]) row[6]);
                    u.setTelefonoUsuario((String) row[7]);
                    u.setCelularUsuario((String) row[8]);
                    u.setStatusUsuario((Integer) row[9]);

                    u.DireccionesJPA = new ArrayList<>();

                    mapUsuarios.put(idUsuario, u);
                }

                DireccionJPA d = new DireccionJPA();
                d.setCalle((String) row[10]);
                d.setNumeroInterior((String) row[11]);
                d.setNumeroExterior((String) row[12]);

                ColoniaJPA c = new ColoniaJPA();
                c.setNombreColonia((String) row[13]);
                c.setCodigoPostal((String) row[14]);

                MunicipioJPA m = new MunicipioJPA();
                m.setNombreMunicipio((String) row[15]);

                EstadoJPA e = new EstadoJPA();
                e.setNombreEstado((String) row[16]);

                PaisJPA p = new PaisJPA();
                p.setNombrePais((String) row[17]);

                e.PaisJPA = p;
                m.EstadoJPA = e;
                c.MunicipioJPA = m;
                d.ColoniaJPA = c;

                u.DireccionesJPA.add(d);
            }

            result.object = new ArrayList<>(mapUsuarios.values());

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result BorradoLogicoUsuario(int idUsuario, int status) {
        Result result = new Result();

        try {
//            UsuarioJPA usuario = entityManager.find(UsuarioJPA.class, idUsuario);
//            
//            usuario.setStatusUsuario(status);
//            
//            entityManager.merge(usuario);

            entityManager.createQuery("UPDATE UsuarioJPA SET StatusUsuario = :status WHERE IdUsuario = :id")
                    .setParameter("status", status)
                    .setParameter("id", idUsuario)
                    .executeUpdate();

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
