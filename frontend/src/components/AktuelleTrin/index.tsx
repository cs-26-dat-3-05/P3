import type { Case } from "../../api/cases";
import type { CaseHistoryEntry } from "../../api/cases";
import { STATUS_MAP } from "../../constants/caseStatus";
import { useCaseHistory } from "../../hooks/useCaseHistory";
import { formatDate } from "../../utils/format";
import {
  Card,
  StepHeader,
  HeaderLabel,
  HeaderTitle,
  StepRow,
  StepCircle,
  StepText,
  StepTitle,
  StepDescription,
  StepDate,
} from "./style";

export interface Step {
  number: number;
  title: string;
  description?: string;
  date?: string;
  status: "done" | "current" | "upcoming";
}

const STEP_DEFINITIONS: {
  title: string;
  description?: string;
  doneAt: Case["status"];
}[] = [
  {
    title: "Returnering registreret",
    description: "Registreret automatisk fra Danske Banks kontoudtog",
    doneAt: "REGISTERED",
  },
  {
    title: "Arrangør identificeret",
    description: "Kontaktoplysninger kontrolleret, herunder e-mail",
    doneAt: "ORGANIZER_IDENTIFIED",
  },
  {
    title: "Nye bankoplysninger efterspurgt",
    description: "Automatisk mail sendt",
    doneAt: "BANK_DETAILS_REQUESTED",
  },
  {
    title: "Afventer korrekt kontonummer",
    doneAt: "BANK_DETAILS_RECEIVED",
  },
  {
    title: "Udbetaling genindsendt",
    doneAt: "RETRANSFERRED",
  },
];

// get latest entry from case log
function findLatestEntry(
  history: CaseHistoryEntry[],
  status: Case["status"],
): CaseHistoryEntry | undefined {
  return history
    .filter((entry) => entry.status === status)
    .reduce<
      CaseHistoryEntry | undefined
    >((latest, entry) => (!latest || entry.createdAt > latest.createdAt ? entry : latest), undefined);
}

function buildSteps(caseData: Case, history: CaseHistoryEntry[]): Step[] {
  const currentStatusStep = STATUS_MAP[caseData.status].step;
  let currentAssigned = false;

  return STEP_DEFINITIONS.map((def, index) => {
    const isDone = currentStatusStep >= STATUS_MAP[def.doneAt].step;

    let status: Step["status"];
    if (isDone) {
      status = "done";
    } else if (!currentAssigned) {
      status = "current";
      currentAssigned = true;
    } else {
      status = "upcoming";
    }

    let date: string | undefined;
    if (isDone) {
      const entry = findLatestEntry(history, def.doneAt);
      if (entry) date = formatDate(entry.createdAt);
      // Fallback for trin 1, hvis historikken mangler
      else if (index === 0) date = formatDate(caseData.createdAt);
    }

    return {
      number: index + 1,
      title: def.title,
      description: isDone ? def.description : undefined,
      date,
      status,
    };
  });
}

type Props = { caseData: Case };

export default function AktuelleTrin({ caseData }: Props) {
  const { history } = useCaseHistory(caseData.id);

  const steps = buildSteps(caseData, history);
  const totalSteps = steps.length;
  const currentStep = steps.find((step) => step.status === "current");

  return (
    <Card>
      <StepHeader>
        <div>
          <HeaderLabel>AKTUELLE TRIN</HeaderLabel>
          <HeaderTitle>
            {currentStep?.title ?? "Sagen er afsluttet"}
          </HeaderTitle>
        </div>
        <span>
          TRIN {currentStep?.number ?? totalSteps} / {totalSteps}
        </span>
      </StepHeader>

      {steps.map((step) => (
        <StepRow key={step.number}>
          <StepCircle $status={step.status}>
            {step.status === "done" ? "✓" : step.number}
          </StepCircle>
          <StepText>
            <StepTitle $status={step.status}>{step.title}</StepTitle>
            {step.description && (
              <StepDescription>{step.description}</StepDescription>
            )}
          </StepText>
          <StepDate>{step.date}</StepDate>
        </StepRow>
      ))}
    </Card>
  );
}
