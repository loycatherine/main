package seedu.address.model.fixedexpense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.fixedexpense.exceptions.DuplicateFixedExpenseException;
import seedu.address.model.fixedexpense.exceptions.FixedExpenseNotFoundException;

class UniqueFixedExpenseListTest {

    //They have a personBuilder, but since we dont have, improvise by using this for now
    public static final FixedExpense FIXED_ACCOMMODATION_EXPENSE =
            new FixedExpense(new Amount("500.00"), new Description("Hotel"),
                    new Category("Accommodation"));

    public static final FixedExpense FIXED_ACTIVITY_EXPENSE =
            new FixedExpense(new Amount("100"), new Description("Disneyland"),
                    new Category("Activity"));

    private static final FixedExpense FIXED_TRANSPORTATION_EXPENSE =
            new FixedExpense(new Amount("1000"), new Description("Airplane"),
                    new Category("Transportation"));

    private final UniqueFixedExpenseList uniqueFixedExpenseList = new UniqueFixedExpenseList();


    @Test
    public void contains_nullFixedExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFixedExpenseList.contains(null));
    }

    @Test
    public void contains_expenseNotInList_returnsFalse() {
        assertFalse(uniqueFixedExpenseList.contains(FIXED_ACCOMMODATION_EXPENSE));
    }

    @Test
    public void contains_expenseInList_returnsTrue() {
        uniqueFixedExpenseList.add(FIXED_ACCOMMODATION_EXPENSE);
        assertTrue(uniqueFixedExpenseList.contains(FIXED_ACCOMMODATION_EXPENSE));
    }

    @Test
    public void contains_expenseWithSameIdentifyFieldsInList_returnsTrue() {
        //Slightly different from UniquePersonListTest, due to different
        //contains convention used for AB3 and for ours.
        uniqueFixedExpenseList.add(FIXED_ACCOMMODATION_EXPENSE);
        FixedExpense editedExpense = new FixedExpense(new Amount("500.00"),
                new Description("hotel"), new Category("Accommodation"));
        assertFalse(uniqueFixedExpenseList.contains(editedExpense));
    }

    @Test
    public void add_nullFixedExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFixedExpenseList.add(null));
    }

    @Test
    public void add_duplicateFixedExpense_throwsDuplicateFixedExpenseException() {
        uniqueFixedExpenseList.add(FIXED_ACTIVITY_EXPENSE);
        assertThrows(DuplicateFixedExpenseException.class, () -> uniqueFixedExpenseList.add(FIXED_ACTIVITY_EXPENSE));
    }

    @Test
    public void setFixedExpense_nullTargetFixedExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFixedExpenseList.setFixedExpense(null,
                FIXED_ACTIVITY_EXPENSE));
    }

    @Test
    public void setFixedExpense_nullFixedExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFixedExpenseList
                .setFixedExpense(FIXED_ACCOMMODATION_EXPENSE,
                null));
    }

    @Test
    public void setFixedExpense_targetFixedExpenseNotInList_throwsFixedExpenseNotFoundException() {
        assertThrows(FixedExpenseNotFoundException.class, () -> uniqueFixedExpenseList.setFixedExpense(
                FIXED_ACCOMMODATION_EXPENSE, FIXED_ACCOMMODATION_EXPENSE));
    }

    @Test
    public void setFixedExpense_editedFixedExpenseIsSameFixedExpense_success() {
        uniqueFixedExpenseList.add(FIXED_ACTIVITY_EXPENSE);
        uniqueFixedExpenseList.setFixedExpense(FIXED_ACTIVITY_EXPENSE, FIXED_ACTIVITY_EXPENSE);
        UniqueFixedExpenseList expectedFixedExpenseList = new UniqueFixedExpenseList();
        expectedFixedExpenseList.add(FIXED_ACTIVITY_EXPENSE);
        assertEquals(expectedFixedExpenseList, uniqueFixedExpenseList);
    }

    @Test
    public void setFixedExpenseHasSameIdentity_success() {
        uniqueFixedExpenseList.add(FIXED_ACTIVITY_EXPENSE);
        FixedExpense editedExpense = new FixedExpense(new Amount("100"), new Description("Disneyland"),
                new Category("Activity"));
        uniqueFixedExpenseList.setFixedExpense(FIXED_ACTIVITY_EXPENSE, editedExpense);
        UniqueFixedExpenseList expectedUniqueFixedExpenseList = new UniqueFixedExpenseList();
        expectedUniqueFixedExpenseList.add(editedExpense);
        assertEquals(expectedUniqueFixedExpenseList, uniqueFixedExpenseList);
    }

    @Test
    public void setFixedExpense_editedFixedExpenseHasDifferentFixedExpense_success() {
        uniqueFixedExpenseList.add(FIXED_ACTIVITY_EXPENSE);
        uniqueFixedExpenseList.setFixedExpense(FIXED_ACTIVITY_EXPENSE, FIXED_ACCOMMODATION_EXPENSE);
        UniqueFixedExpenseList expectedFixedExpenseList = new UniqueFixedExpenseList();
        expectedFixedExpenseList.add(FIXED_ACCOMMODATION_EXPENSE);
        assertEquals(expectedFixedExpenseList, uniqueFixedExpenseList);
    }

    @Test
    public void setFixedExpense_editedFixedExpenseHasNonUniqueIdentity_throwsDuplicateFixedExpenseException() {
        uniqueFixedExpenseList.add(FIXED_ACCOMMODATION_EXPENSE);
        uniqueFixedExpenseList.add(FIXED_TRANSPORTATION_EXPENSE);
        assertThrows(DuplicateFixedExpenseException.class, () -> uniqueFixedExpenseList.setFixedExpense
                (FIXED_ACCOMMODATION_EXPENSE, FIXED_TRANSPORTATION_EXPENSE));
    }

    @Test
    public void remove_nullFixedExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFixedExpenseList.remove(null));
    }

    @Test
    public void remove_fixedExpenseDoesNotExist_throwsFixedExpenseNotFoundException() {
        assertThrows(FixedExpenseNotFoundException.class, () -> uniqueFixedExpenseList
                .remove(FIXED_ACCOMMODATION_EXPENSE));
    }

    @Test
    public void remove_existingFixedExpense_removesFixedExpense() {
        uniqueFixedExpenseList.add(FIXED_TRANSPORTATION_EXPENSE);
        uniqueFixedExpenseList.remove(FIXED_TRANSPORTATION_EXPENSE);
        UniqueFixedExpenseList expectedUniquePersonList = new UniqueFixedExpenseList();
        assertEquals(expectedUniquePersonList, uniqueFixedExpenseList);
    }

    @Test
    public void setFixedExpense_nullUniqueFixedExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFixedExpenseList
                .setFixedExpenses((UniqueFixedExpenseList) null));
    }

    @Test
    public void setFixedExpense_uniqueFixedExpense_replacesOwnListWithProvidedUniqueFixedExpenseList() {
        uniqueFixedExpenseList.add(FIXED_TRANSPORTATION_EXPENSE);
        UniqueFixedExpenseList expectedFixedExpenseList = new UniqueFixedExpenseList();
        uniqueFixedExpenseList.add(FIXED_ACCOMMODATION_EXPENSE);
        uniqueFixedExpenseList.setFixedExpenses(expectedFixedExpenseList);
        assertEquals(expectedFixedExpenseList, uniqueFixedExpenseList);
    }

    @Test
    public void setFixedExpense_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFixedExpenseList
                .setFixedExpenses((List<FixedExpense>) null));
    }

    @Test
    public void setFixedExpense_list_replacesOwnListWithProvidedList() {
        uniqueFixedExpenseList.add(FIXED_TRANSPORTATION_EXPENSE);
        List<FixedExpense> fixedExpenseList = Collections.singletonList(FIXED_ACCOMMODATION_EXPENSE);
        uniqueFixedExpenseList.setFixedExpenses(fixedExpenseList);
        UniqueFixedExpenseList expectedUniqueFixedExpenseList = new UniqueFixedExpenseList();
        expectedUniqueFixedExpenseList.add(FIXED_ACCOMMODATION_EXPENSE);
        assertEquals(expectedUniqueFixedExpenseList, uniqueFixedExpenseList);
    }

    @Test
    public void setFixedExpense_listWithDuplicateFixedExpense_throwsDuplicatePersonException() {
        List<FixedExpense> listWithDuplicateFixedExpense = Arrays
                .asList(FIXED_ACCOMMODATION_EXPENSE, FIXED_ACCOMMODATION_EXPENSE);
        assertThrows(DuplicateFixedExpenseException.class, ()
            -> uniqueFixedExpenseList.setFixedExpenses(listWithDuplicateFixedExpense));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFixedExpenseList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
        //Same Hash Code
        uniqueFixedExpenseList.add(FIXED_ACCOMMODATION_EXPENSE);
        UniqueFixedExpenseList ue = new UniqueFixedExpenseList();
        ue.add(FIXED_ACCOMMODATION_EXPENSE);
        assertEquals(uniqueFixedExpenseList.hashCode() , ue.hashCode());

        //Different Hash code
        UniqueFixedExpenseList diffUe = new UniqueFixedExpenseList();
        diffUe.add(FIXED_ACTIVITY_EXPENSE);
        assertNotEquals(diffUe.hashCode(), uniqueFixedExpenseList.hashCode());
    }
}
