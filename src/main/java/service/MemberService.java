package service;

import javafx.collections.ObservableList;
import models.BookModel;
import models.MemberModel;
import repositories.MemberRepository;

public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService() {
        this.memberRepository = new MemberRepository();
    }

    public ObservableList<MemberModel> getAllMembers() {

        return memberRepository.getAllMembers();
    }

    public boolean addMember(MemberModel memberModel) {

        return memberRepository.insert(memberModel);
    }

    public boolean deleteMember(int id) {

        return memberRepository.deleteMember(id);
    }

    public boolean updateMember(MemberModel memberModel) {

        return memberRepository.updateMember(memberModel);
    }

    public int getMaleMemberCount() {

        return memberRepository.countMale();
    }

    public int getFemaleMemberCount() {

        return memberRepository.countFemale();
    }
}
