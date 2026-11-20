import styled from "styled-components";
import AktuelleTrin from "../AktuelleTrin";
import InternalNotes from "../InternalNotes";
import type { Case } from "../../api/cases";
import type { Note } from "../../api/notes";

const CaseDetailsWrapper = styled.div`
  display: grid;
  grid-template-columns: 3fr 2fr;
  gap: 24px;
  margin-top: 24px;
`;

type Props = { caseData: Case; notes: Note[] };

export default function CaseDetails({ caseData, notes }: Props) {
  return (
    <CaseDetailsWrapper>
      <AktuelleTrin caseData={caseData} />
      <InternalNotes caseId={caseData.id} notes={notes} />
    </CaseDetailsWrapper>
  );
}
