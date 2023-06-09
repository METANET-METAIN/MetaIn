//package com.metain.web.service;
//
//import com.metain.web.domain.Emp;
//import com.metain.web.dto.PrincipalDetails;
//import com.metain.web.mapper.HrMapper;
//import com.metain.web.mapper.MemberMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class HomeService implements UserDetailsService {
//
//    @Autowired
//    private MemberMapper memberMapper;
//
//    @Autowired
//    private HrMapper hrMapper;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
////    db로부터 사원정보를 가져와 사원인지 아닌지를 체크하는 메소드
//    @Override
//    public UserDetails loadUserByUsername(String empSabun) throws UsernameNotFoundException {
//        System.out.println("empSabun" + empSabun);
//        // DB로부터 회원 정보를 가져온다.
//        Emp emp = memberMapper.login(empSabun, "");
//
//        if (emp == null) {
//            throw new UsernameNotFoundException(empSabun + "의 사원이 존재하지 않습니다.");
//        }
//        return new PrincipalDetails(emp); // UserDetails 클래스를 상속받은 PrincipalDetails 리턴한다.
//    }
//
//
//
//
//
//
//
//
//
//
//
////    db로부터 사원정보를 가져와 사원인지 아닌지를 체크하는 메소드
////    @Override
////    public UserDetails loadUserByUsername(String empSabun) throws UsernameNotFoundException {
////
////        //DB로부터 회원 정보를 가져온다.
////        ArrayList<Emp> empAuthes = memberMapper.login(empSabun);
////
////        if(empAuthes.size() == 0) {
////            throw new UsernameNotFoundException(empSabun + "의 사원이 존재하지 않습니다.");
////        }
////        return new PrincipalDetails(empAuthes); //UserDetails 클래스를 상속받은 PrincipalDetails 리턴한다.
////    }
//
////    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
////    public String InsertEmp(Emp emp, NewEmp newEmp){
////       emp.setEmpPwd(bCryptPasswordEncoder.encode(emp.getEmpPwd()));
//
////       int flag = hrMapper.insertNewEmp(newEmp);
////       if (flag > 0) {
////           int empNo = memberMapper.findEmpNo(emp.getEmpSabun());
////           int roleNo = memberMapper.findRoleNo(emp.getEmpGrade());
////
////
////
////
////
////
////
////       }
////    }
//
//
//}
