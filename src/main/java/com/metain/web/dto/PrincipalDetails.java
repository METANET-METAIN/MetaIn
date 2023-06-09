//package com.metain.web.dto;
//
//import com.metain.web.domain.Emp;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//
////로그인에 성공하면 Security Session을 생성해 줌 (Key값 : Security ContextHolder)
////Security Session(Authentication(UserDetails)) 이런 식의 구조로 되어있는데
//// PrincipalDetails에서 UserDetails를 설정해준다고 보면 됨
//public class PrincipalDetails implements UserDetails {
//
////    private ArrayList<Emp> emp;
//
//    private Emp emp;
//
////    public PrincipalDetails (ArrayList<Emp> empAuthes) {
////        this.emp = empAuthes;
////    }
//
//    public PrincipalDetails(Emp emp){
//
//        if (emp == null) {
//            throw new IllegalArgumentException("Emp : null");
//        }
//        this.emp = emp;
//    }
//
////    권한 관련 작업을 하기 위한 role return
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collections = new ArrayList<>();
//        collections.add(() -> {
//            return emp.getEmpGrade().name();
//        });
//
//        return collections;
//    }
//
//
//    @Override
//    public String getPassword() {
//        return emp.getEmpPwd();
//    }
//
//    // get Username 메서드 (생성한 User은 loginId 사용)
//    @Override
//    public String getUsername() {
//        return emp.getEmpSabun();
//    }
//
//
//    public Emp empList(Emp emp) {
//        return empList(emp);
//    }
//
//
//
//
////    권한 관련 작업을 하기 위한 role return
////    @Override
////    public Collection<? extends GrantedAuthority> getAuthorities() { //사원이 갖고 있는 권한 목록
////        List<GrantedAuthority> authorities  = new ArrayList<GrantedAuthority>();
////
////        for(int x = 0; x < emp.size(); x++){
////            authorities.add(new SimpleGrantedAuthority(emp.get(x).getEmpGrade()));
////        }
////        return authorities;
////    }
////
////    @Override
////    public String getPassword() {
////        return emp.get(0).getEmpPwd();
////    }
////
////    // get Username 메서드 (생성한 User은 loginId 사용)
////    @Override
////    public String getUsername() {
////        return emp.get(0).getEmpSabun();
////    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//
//}
