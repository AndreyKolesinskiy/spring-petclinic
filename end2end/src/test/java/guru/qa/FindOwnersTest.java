package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import guru.qa.owner.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FindOwnersTest {

	private OwnersManager om = new OwnersManager();
	private int createdOwnerId;

	@BeforeEach
	void addOwner() {
		createdOwnerId = om.createOwner(Owner.builder()
			.firstName("Andrei")
			.lastName("Kaliasinski")
			.address("Belarus")
			.city("Grodno")
			.telephone("5555555")
			.build());
	}

//	@AfterEach
//	void releaseOwner() {
//		om.deleteOwner(createdOwnerId);
//	}

	@Test
	void findOwnersTest() {
		//System.out.println("### Generated id: " + createdOwnerId);
		Selenide.open("http://localhost:8080/owners/find");
		$("#lastName").setValue("Kaliasinski");
		$("button[type='submit']").click();
		$("table.table").should(visible);
		$$("tr").find(text("Name")).should(text("Andrei Kaliasinski"));
	}
}
