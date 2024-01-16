package dao.impl;

import dao.SellerDao;
import db.DB;
import db.DbException;
import entities.Department;
import entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private final Connection connection;

    public SellerDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;

        try{
            st = connection.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0){
                ResultSet set = st.getGeneratedKeys();
                if (set.next()){
                    int id = set.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(set);
            }else {
                throw new DbException("No lines were affected!");
            }

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;

        try{
            st = connection.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ? "
            );

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());

            st.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }


    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("DELETE FROM seller WHERE Id = ?");

            statement.setInt(1, id);

            int rows =  statement.executeUpdate();

            if (rows == 0){
                throw new DbException("Id not exist! ");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public Seller findById(Integer id) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                preparedStatement = connection.prepareStatement(
                        "SELECT seller.*,department.Name as DepName "
                                + "FROM seller INNER JOIN department "
                                + "ON seller.DepartmentId = department.Id "
                                + "WHERE seller.Id = ? "
                );

                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Department department = instantiateDepartment(resultSet);
                    Seller seller = instantiateSeller(resultSet, department);
                    return seller;
                }
                return null;

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(preparedStatement);
                DB.closeResultSet(resultSet);
            }

    }

    private Department instantiateDepartment(ResultSet resultSet)throws SQLException{
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department)throws SQLException{
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement statement = null;
        ResultSet set = null;

        Department depOne = new Department();
        try {
            statement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name "
            );

            set = statement.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>(); // estrutura map ora nao repetir

            while (set.next()) {

                Department dep = departmentMap.get(set.getInt("DepartmentId"));

                if (dep == null){
                    depOne = instantiateDepartment(set);
                    departmentMap.put(set.getInt("DepartmentId"), depOne);
                }


                Seller seller = instantiateSeller(set, depOne);
                sellerList.add(seller);
            }
            return sellerList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(set);
        }
    }

    @Override
    public List<Seller> FindByIdDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                     + "FROM seller INNER JOIN department "
                     + "ON seller.DepartmentId = department.Id "
                     + "WHERE DepartmentId = ? "
                     + "ORDER BY Name "
            );

            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>(); // estrutura map ora nao repetir

            while (resultSet.next()) {

                Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));

                if (dep == null){
                    department = instantiateDepartment(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), department);
                }


                Seller seller = instantiateSeller(resultSet, department);
                sellerList.add(seller);
            }
            return sellerList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
}
